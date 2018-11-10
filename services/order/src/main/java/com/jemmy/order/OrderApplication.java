package com.jemmy.order;

import com.jemmy.common.redisson.config.RedissonAutoConfiguration;
import com.jemmy.common.redisson.config.RedissonSpringAutoConfiguration;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @author Jemmy
 */
@SpringCloudApplication
@EnableFeignClients
@EnableOAuth2Client
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan(basePackages = {"com.jemmy.order.mapper"})
@Import({RedissonAutoConfiguration.class, RedissonSpringAutoConfiguration.class})
@ComponentScan(basePackages = {"com.jemmy.order", "com.jemmy.common.web.exception", "com.jemmy.common.security.oauth2", "com.jemmy.common.aop","com.jemmy.common.mybatis"})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
    @Bean
    public SpringLiquibase springLiquibase(DataSource dataSource) {

        SpringLiquibase springLiquibase = new SpringLiquibase();

        springLiquibase.setDataSource(dataSource);
        springLiquibase.setChangeLog("classpath:/liquibase/index.xml");

        return springLiquibase;
    }
}
