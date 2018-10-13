package com.jemmy.eventdriven;

import com.jemmy.eventdriven.mapper.EventSubscriberMapper;
import com.jemmy.eventdriven.model.EventSubscriber;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Jemmy
 */
public abstract class EventHandler {
    @Autowired
    private EventSubscriberMapper mapper;

    public EventSubscriberMapper getMapper() {
        return mapper;
    }

    public void setMapper(EventSubscriberMapper mapper) {
        this.mapper = mapper;
    }

    public abstract void handle(EventSubscriber subscriber);
}
