package com.jemmy.eventdriven;



import com.jemmy.eventdriven.mapper.EventPublisherMapper;
import com.jemmy.eventdriven.model.EventPublisher;
import com.jemmy.eventdriven.model.type.EventStatus;

import java.util.Set;

/**
 * @author Jemmy
 */
public enum PublishNewEventStrategy implements BatchFetchEventStrategy {
    /**
     * 基于枚举的单例
     */
    SINGLETON;

    @Override
    public Set<EventPublisher> execute(EventPublisherMapper mapper) {
        return mapper.selectLimitedEntityByEventStatus(EventStatus.NEW, 300);
    }
}
