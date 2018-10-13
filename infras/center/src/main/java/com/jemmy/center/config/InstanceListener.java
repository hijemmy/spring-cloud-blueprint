package com.jemmy.center.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

/**
 * 监听服务事件
 */
@Configuration
public class InstanceListener{
    private static Logger logger=LoggerFactory.getLogger(InstanceListener.class);

    /**
     * 监听服务挂掉事件
     */
    @Configuration
    class InstanceCancel implements ApplicationListener<EurekaInstanceCanceledEvent> {
        @Override
        public void onApplicationEvent(EurekaInstanceCanceledEvent event) {
            logger.info("服务:{}挂了", event.getAppName());
        }
    }

    @Configuration
    class InstanceRegister implements ApplicationListener<EurekaInstanceRegisteredEvent>{

        @Override
        public void onApplicationEvent(EurekaInstanceRegisteredEvent event) {
            logger.info("服务：{}，注册成功了",event.getInstanceInfo().getAppName());
        }
    }
/*
    @Configuration
    class InstanceRenew implements ApplicationListener<EurekaInstanceRenewedEvent>{

        @Override
        public void onApplicationEvent(EurekaInstanceRenewedEvent event) {
            logger.info("心跳检测服务：{}" ,event.getInstanceInfo().getAppName());
        }
    }*/
}
