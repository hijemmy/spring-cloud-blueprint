package com.jemmy.user;

import com.jemmy.common.mybatis.mapper.RootMapper;
import com.jemmy.common.redisson.config.RedissonAutoConfiguration;
import com.jemmy.common.redisson.config.RedissonSpringAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import tk.mybatis.spring.annotation.MapperScan;

@SpringCloudApplication
@EnableFeignClients
@EnableOAuth2Client
@Import({RedissonAutoConfiguration.class, RedissonSpringAutoConfiguration.class})
@MapperScan(basePackages = "com.jemmy.user.mapper",markerInterface = RootMapper.class)
@ComponentScan({"com.jemmy.user","com.jemmy.common.aop","com.jemmy.common.web.exception", "com.jemmy.common.security.oauth2"})

public class UserApplication {
    @Bean
    MethodValidationPostProcessor methodValidationPostProcessor(){
        return  new MethodValidationPostProcessor();
    }
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
