package com.jemmy.eventdriven.model.type.handler;


import com.jemmy.common.mybatis.handler.GenericTypeHandler;
import com.jemmy.eventdriven.model.type.EventStatus;

/**
 * @author Jemmy
 */
public class EventStatusTypeHandler extends GenericTypeHandler<EventStatus> {
    @Override
    public int getEnumIntegerValue(EventStatus parameter) {
        return parameter.getStatus();
    }
}
