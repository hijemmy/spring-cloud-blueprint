package com.jemmy.product;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringCloudApplication
@EnableFeignClients
@EnableOAuth2Client
@EnableAspectJAutoProxy(exposeProxy = true)
@ComponentScan(basePackages = {"com.jemmy.product", "com.jemmy.common.web.exception", "com.jemmy.common.security.oauth2"})
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
