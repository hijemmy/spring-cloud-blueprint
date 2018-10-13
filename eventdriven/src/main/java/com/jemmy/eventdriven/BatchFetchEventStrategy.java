package com.jemmy.eventdriven;

import com.jemmy.eventdriven.mapper.EventPublisherMapper;
import com.jemmy.eventdriven.model.EventPublisher;

import java.util.Set;

/**
 * @author Jemmy
 */
public interface BatchFetchEventStrategy {
    Set<EventPublisher> execute(EventPublisherMapper mapper);
}
