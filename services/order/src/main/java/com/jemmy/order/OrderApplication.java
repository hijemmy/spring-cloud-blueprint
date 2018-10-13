package com.jemmy.order;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * @author Jemmy
 */
@SpringCloudApplication
@EnableFeignClients
@EnableOAuth2Client
@EnableAspectJAutoProxy(exposeProxy = true)
@ComponentScan(basePackages = {"com.jemmy.order", "com.jemmy.common.web.exception", "com.jemmy.common.security.oauth2", "com.jemmy.common.aop"})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
