package com.jemmy.eventdriven.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jemmy.eventdriven.PublishNewEventStrategy;
import com.jemmy.eventdriven.RepublishPendingEventStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Profile("!dev")
@Slf4j
public class NonDevEventDrivenPublisher extends AbstractEventDrivenPublisher{

    /**
     * 扫描定量的NEW事件，发布至Broker之后更新为PENDING
     */
    @Override
    @Scheduled(fixedRate = 700)
    public void fetchAndPublishEventInNewStatus() {
        try {
            fetchAndPublishToBroker(PublishNewEventStrategy.SINGLETON);
        } catch (JsonProcessingException e) {
            log.error("{}",e.getMessage());
        }
    }

    /**
     * 扫面定量的PENDING事件并重新发布至Broker，意在防止实例因为意外宕机导致basic.return和basic.ack的状态丢失。
     */
    @Override
    @Scheduled(fixedRate = 5000)
    public void fetchAndRepublishEventInPendingStatus() {
        try {
            fetchAndPublishToBroker(RepublishPendingEventStrategy.SINGLETON);
        } catch (JsonProcessingException e) {
            log.error("{}",e.getMessage());
        }
    }
}
