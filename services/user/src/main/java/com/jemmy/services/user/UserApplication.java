package com.jemmy.services.user;

import com.jemmy.common.core.mybatis.RootMapper;
import com.jemmy.common.redisson.config.RedissonAutoConfiguration;
import com.jemmy.common.redisson.config.RedissonSpringAutoConfiguration;
import com.jemmy.common.security.core.code.sms.SmsCodeSender;
import com.jemmy.services.user.service.impl.PcSmsCodeSender;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Jemmy
 */
@SpringCloudApplication
@EnableFeignClients(basePackages = {"com.jemmy.apis"})
@EnableOAuth2Client
@Import({RedissonAutoConfiguration.class, RedissonSpringAutoConfiguration.class})
@MapperScan(basePackages = {"com.jemmy.services.user.mapper","com.jemmy.apis.rmq.mapper"},markerInterface = RootMapper.class)
@ComponentScan({"com.jemmy.common","com.jemmy.apis","com.jemmy.services.user"})
public class UserApplication {
    @Bean
    MethodValidationPostProcessor methodValidationPostProcessor(){
        return  new MethodValidationPostProcessor();
    }
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }


    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:org/springframework/security/messages_zh_CN");
        return messageSource;
    }

    @Bean
    public SmsCodeSender smsCodeSender() {
        return new PcSmsCodeSender();
    }

}
