package com.jemmy.eventdriven.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * @author Jemmy
 */
@Service
@Profile("dev")
@Slf4j
public class DevEventDrivenPublisher extends AbstractEventDrivenPublisher{

    /**
     * 扫描定量的NEW事件，发布至Broker之后更新为PENDING
     */
    @Override
    public void fetchAndPublishEventInNewStatus() {
        log.debug("定时扫描,将NEW变为PENDING");
    }

    /**
     * 扫面定量的PENDING事件并重新发布至Broker，意在防止实例因为意外宕机导致basic.return和basic.ack的状态丢失。
     */
    @Override
    public void fetchAndRepublishEventInPendingStatus() {
       log.debug("定量扫描,重发PENDING数据");
    }
}
