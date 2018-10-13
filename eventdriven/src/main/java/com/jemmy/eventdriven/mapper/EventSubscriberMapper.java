package com.jemmy.eventdriven.mapper;

import com.jemmy.common.mapper.RootMapper;
import com.jemmy.eventdriven.model.EventSubscriber;
import com.jemmy.eventdriven.model.type.EventStatus;
import org.apache.ibatis.annotations.Param;

public interface EventSubscriberMapper extends RootMapper<EventSubscriber> {

    int updateEventStatusByPrimaryKeyInCasMode(@Param("id") Long id, @Param("expect") EventStatus expect, @Param("target") EventStatus target);

}