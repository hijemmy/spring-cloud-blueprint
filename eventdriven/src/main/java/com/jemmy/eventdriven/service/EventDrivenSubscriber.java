package com.jemmy.eventdriven.service;

import com.google.common.base.Preconditions;
import com.jemmy.common.event.EventBusinessType;
import com.jemmy.eventdriven.EventHandler;
import com.jemmy.eventdriven.mapper.EventSubscriberMapper;
import com.jemmy.eventdriven.model.EventSubscriber;
import com.jemmy.eventdriven.model.type.EventStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * @author Jemmy
 */
@Slf4j
@Service
public class EventDrivenSubscriber {

    @Autowired
    private EventSubscriberMapper subscriberMapper;
    @Autowired
    private EventHandler handler;

    @PostConstruct
    public void afterProperties() {
        Preconditions.checkState(handler != null, "root EventHandler MUST not be null");
    }

    @Transactional(rollbackFor = Exception.class)
    public int persistAndHandleMessage(EventBusinessType businessType, String payload, String guid) {
        Preconditions.checkNotNull(businessType);
        Preconditions.checkNotNull(payload);
        Preconditions.checkNotNull(guid);
        final EventSubscriber subscriber = new EventSubscriber();
        subscriber.setBusinessType(businessType);
        subscriber.setPayload(payload);
        subscriber.setGuid(guid);
        subscriber.setLockVersion(0);
        subscriber.setEventStatus(EventStatus.NEW);
        int influence = 0;
        try {
            influence = subscriberMapper.insert(subscriber);
        } catch (DuplicateKeyException e) {
            log.info("duplicate key in processing message '{}'", guid);
        }
        // 非重复消息则执行实际的业务
        if (influence > 0) {
            handler.handle(subscriber);
        }
        return influence;
    }

}
