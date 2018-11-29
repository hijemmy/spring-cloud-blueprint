package com.jemmy.services.edriven;

import com.jemmy.common.core.mybatis.RootMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by pc on 2018/11/10.
 */
@SpringCloudApplication
@EnableFeignClients(basePackages = {"com.jemmy.apis"})
@EnableOAuth2Client
@MapperScan(basePackages = {"com.jemmy.services.edriven.mapper","com.jemmy.apis.rmq.mapper"},markerInterface = RootMapper.class)
@ComponentScan({"com.jemmy.common.zk","com.jemmy.common.core","com.jemmy.common.security.feign","com.jemmy.apis","com.jemmy.services.edriven"})
public class EDriventApplication {
    public static void main(String[] args) {
        SpringApplication.run(EDriventApplication.class, args);
    }
}
