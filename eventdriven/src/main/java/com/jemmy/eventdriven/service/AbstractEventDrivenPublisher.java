package com.jemmy.eventdriven.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.jemmy.common.event.EventBusinessType;
import com.jemmy.common.model.event.Event;
import com.jemmy.eventdriven.BatchFetchEventStrategy;
import com.jemmy.eventdriven.MessageRoute;
import com.jemmy.eventdriven.mapper.EventPublisherMapper;
import com.jemmy.eventdriven.model.EventPublisher;
import com.jemmy.eventdriven.model.type.EventStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Jemmy
 */
@Slf4j
public abstract class AbstractEventDrivenPublisher {

    @Autowired
    private EventPublisherMapper publisherMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    private static final ConcurrentMap<EventBusinessType, MessageRoute> REGISTRY = new ConcurrentHashMap<>();

    @PostConstruct
    public void postConstruct() {
        // return
        rabbitTemplate.setReturnCallback(new RabbitReturnCallback());
        // ack
        rabbitTemplate.setConfirmCallback(new RabbitConfirmCallback());
    }

    /**
     * 扫描定量的NEW事件，发布至Broker之后更新为PENDING
     */
    public abstract void fetchAndPublishEventInNewStatus();

    /**
     * 扫面定量的PENDING事件并重新发布至Broker，意在防止实例因为意外宕机导致basic.return和basic.ack的状态丢失。
     */
    public abstract void fetchAndRepublishEventInPendingStatus();

    /**
     * 根据传入的业务类型取出所设定的exchange与routeKey
     */
    public static MessageRoute getMessageRoute(EventBusinessType businessType) {
        Preconditions.checkNotNull(businessType);
        return REGISTRY.get(businessType);
    }

    /**
     * 所有的业务类型都必须先注册exchange与routeKey才能使用，而不是将exchange与routeKey持久化，浪费大量磁盘空间。
     */
    public static void registerType(EventBusinessType businessType, String exchange, String routeKey) {
        Preconditions.checkNotNull(businessType);
        Preconditions.checkNotNull(exchange);
        Preconditions.checkNotNull(routeKey);
        REGISTRY.put(businessType, new MessageRoute(exchange, routeKey));
    }

    /**
     * 判断业务类型是否有被注册
     */
    public static boolean includesType(EventBusinessType businessType) {
        Preconditions.checkNotNull(businessType);
        return REGISTRY.containsKey(businessType);
    }

    public static void throwIfNotIncluded(EventBusinessType businessType) {
        Preconditions.checkNotNull(businessType);
        Preconditions.checkArgument(includesType(businessType), "该业务类型尚未注册");
    }

    /**
     * 消息落地
     */
    @Transactional(rollbackFor = Exception.class)
    public  int persistPublishMessage(Object payload,EventBusinessType businessType) throws JsonProcessingException {
        Preconditions.checkNotNull(payload);
        // 严格控制发往Broker的业务类型
        throwIfNotIncluded(businessType);
        final EventPublisher publisher = new EventPublisher();
        publisher.setEventStatus(EventStatus.NEW);
        publisher.setGuid(UUID.randomUUID().toString());
        publisher.setLockVersion(0);
        publisher.setPayload(objectMapper.writeValueAsString(payload));
        publisher.setBusinessType(businessType);
        return publisherMapper.insert(publisher);
    }

    /**
     * 发布消息至Broker，通常由定时器扫描发送
     */
    public void publish(Event event, String exchange, String routeKey, CorrelationData correlationData) throws JsonProcessingException {
        Preconditions.checkNotNull(event);
        Preconditions.checkNotNull(exchange);
        Preconditions.checkNotNull(routeKey);
        log.debug("发布消息:{},exchange:{},routekey:{}",event,exchange,routeKey);
        rabbitTemplate.convertAndSend(exchange, routeKey, event, correlationData);
    }

    /**
     * 按照指定的策略将指定状态的事件(通常为NEW与PENDING)发布至Broker
     */
    @Transactional(rollbackFor = Exception.class)
    public void fetchAndPublishToBroker(BatchFetchEventStrategy fetchStrategy) throws JsonProcessingException {
        Preconditions.checkNotNull(fetchStrategy);
        final Set<EventPublisher> events = fetchStrategy.execute(publisherMapper);
        for (EventPublisher event : events) {
            final EventBusinessType type = event.getBusinessType();
            if (includesType(type)) {
                // 发送
                final MessageRoute route = getMessageRoute(type);
                // DTO转换
                final Event dto = new Event();
                dto.setBusinessType(type);
                dto.setGuid(event.getGuid());
                dto.setPayload(event.getPayload());
                // 更新状态为'处理中'顺便刷新一下update_time
                event.setEventStatus(EventStatus.PENDING);
                // 意在多实例的情况下不要重复刷新
                if (publisherMapper.updateByPrimaryKeySelectiveWithOptimisticLock(event) > 0) {
                    // 正式发送至Broker
                    publish(dto, route.getExchange(), route.getRouteKey(), new CorrelationData(String.valueOf(event.getId())));
                }
            } else {
                // 找不到的路由的情况,表示不归本事件订阅器处理,留空
                /*event.setEventStatus(EventStatus.FAILED);
                if (publisherMapper.updateByPrimaryKeySelectiveWithOptimisticLock(event) > 0) {
                    log.warn("事件尚未注册不能被发送至Broker, id: {}, guid: {}，目前已将该事件置为FAILED，待审查过后人工将状态校正", event.getId(), event.getGuid());
                }*/
            }
        }
    }

    /**
     * 在Mandatory下，当exchange存在但无法路由至queue的情况下记录入库
     * <p>
     * basic.return（basic.return将会发生在basic.ack之前）
     */
    private class RabbitReturnCallback implements RabbitTemplate.ReturnCallback {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            final String failedMessage = new String(message.getBody(), Charset.forName("utf-8"));
            try {

                final String guid = objectMapper.readValue(failedMessage,Event.class).getGuid();
                final EventPublisher publisher = new EventPublisher();
                publisher.setGuid(guid);
                if (EventStatus.NO_ROUTE.name().equalsIgnoreCase(replyText)) {
                    publisher.setEventStatus(EventStatus.NO_ROUTE);
                } else {
                    logReturnedFault(replyCode, replyText, exchange, routingKey, failedMessage);
                    publisher.setEventStatus(EventStatus.ERROR);
                }
                // 因为在basic.return之后会调用basic.ack，鄙人认为NO_ROUTE的状态有可能被错误地转换成为NOT_FOUND，所以不需要考虑竞争情况
                publisherMapper.updateByGuidSelective(publisher);
            } catch (IOException e) {
                logReturnedFault(replyCode, replyText, exchange, routingKey, failedMessage);
            }
        }

        private void logReturnedFault(int replyCode, String replyText, String exchange, String routingKey, String failedMessage) {
            log.error("no route for message and failed to read it: {}, replyCode: {}, replyText: {}, " +
                    "exchange: {}, routeKey: {}", failedMessage, replyCode, replyText, exchange, routingKey);
        }
    }

    /**
     * 确认Broker接收消息的状态
     * <p>
     * basic.ack
     */
    private class RabbitConfirmCallback implements RabbitTemplate.ConfirmCallback {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            final Long id = Long.valueOf(correlationData.getId());
            // 当一条消息为PENDING而且ack为true时则删除原有的消息
            if (ack) {
                // flag instead
                publisherMapper.updateEventStatusByPrimaryKeyInCasMode(id, EventStatus.PENDING, EventStatus.DONE);
                // 或直接删除
                //publisherMapper.deleteByPrimaryKey(id);
            } else {
                // 打开mandatory之后，ack为false的情况就是没有找到exchange
                log.error("message has failed to found a proper exchange which local id is {}. cause: {}", id, cause);
                publisherMapper.updateEventStatusByPrimaryKeyInCasMode(id, EventStatus.PENDING, EventStatus.NOT_FOUND);
            }
        }
    }

}
