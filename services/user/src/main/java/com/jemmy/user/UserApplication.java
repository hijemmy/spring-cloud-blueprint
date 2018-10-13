package com.jemmy.user;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringCloudApplication
@EnableFeignClients
@EnableOAuth2Client
public class UserApplication {
    @Bean
    MethodValidationPostProcessor methodValidationPostProcessor(){
        return  new MethodValidationPostProcessor();
    }
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
