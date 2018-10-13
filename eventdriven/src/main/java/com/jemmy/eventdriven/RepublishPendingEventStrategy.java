package com.jemmy.eventdriven;

import com.jemmy.eventdriven.mapper.EventPublisherMapper;
import com.jemmy.eventdriven.model.EventPublisher;
import com.jemmy.eventdriven.model.type.EventStatus;

import java.time.OffsetDateTime;
import java.util.Set;

/**
 * @author Jemmy
 */
public enum RepublishPendingEventStrategy implements BatchFetchEventStrategy {
    /**
     * 基于枚举的单例
     */
    SINGLETON;

    @Override
    public Set<EventPublisher> execute(EventPublisherMapper mapper) {
        // 取出3秒前已经发送过至队列但是没有收到ack请求的消息，并进行重试
        return mapper.selectLimitedEntityByEventStatusBeforeTheSpecifiedUpdateTime(EventStatus.PENDING, 300, OffsetDateTime.now().minusSeconds(3));
    }
}
