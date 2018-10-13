package com.jemmy.eventdriven;

import com.google.common.base.Preconditions;
import com.jemmy.eventdriven.model.EventSubscriber;
import com.jemmy.eventdriven.model.type.EventStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zhao Junjian
 */
@Slf4j
public class NopeEventHandler extends EventHandler {

    @Override
    public void handle(EventSubscriber subscriber) {
        Preconditions.checkNotNull(subscriber);
        Preconditions.checkNotNull(subscriber.getId());
        if (getMapper().updateEventStatusByPrimaryKeyInCasMode(subscriber.getId(), EventStatus.NEW, EventStatus.NOT_FOUND) > 0) {
            log.error("event which id is {} has to change status from NEW to NOT_FOUND due to threr is not a match handler.", subscriber.getId());
        }
    }

}
