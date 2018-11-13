package com.jemmy.services.edriven;

import com.jemmy.common.core.mybatis.MyMapper;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * Created by pc on 2018/11/10.
 */
@SpringCloudApplication
@EnableFeignClients(basePackages = {"com.jemmy.apis"})
@EnableOAuth2Client
@MapperScan(basePackages = {"com.jemmy.services.edriven.mapper","com.jemmy.apis.rmq.mapper"},markerInterface = MyMapper.class)
@ComponentScan({"com.jemmy.common.config","com.jemmy.apis","com.jemmy.services.edriven"})
public class EDriventApplication {
    public static void main(String[] args) {
        SpringApplication.run(EDriventApplication.class, args);
    }
    @Bean
    public SpringLiquibase springLiquibase(DataSource dataSource) {

        SpringLiquibase springLiquibase = new SpringLiquibase();

        springLiquibase.setDataSource(dataSource);
        springLiquibase.setChangeLog("classpath:/liquibase/index.xml");

        return springLiquibase;
    }
}
