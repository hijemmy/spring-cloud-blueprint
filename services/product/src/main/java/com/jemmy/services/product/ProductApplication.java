package com.jemmy.services.product;

import com.jemmy.common.core.mybatis.RootMapper;
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

@SpringCloudApplication
@EnableFeignClients(basePackages = "com.jemmy.apis")
@EnableOAuth2Client
@EnableAspectJAutoProxy(exposeProxy = true)
@Import({RedissonAutoConfiguration.class, RedissonSpringAutoConfiguration.class})
@MapperScan(basePackages = {"com.jemmy.services.product.mapper","com.jemmy.apis.rmq.mapper"},markerInterface = RootMapper.class)
@ComponentScan({"com.jemmy.common.zk","com.jemmy.common.core","com.jemmy.common.security.feign","com.jemmy.apis","com.jemmy.services.product"})
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
    @Bean
    public SpringLiquibase springLiquibase(DataSource dataSource) {

        SpringLiquibase springLiquibase = new SpringLiquibase();

        springLiquibase.setDataSource(dataSource);
        springLiquibase.setChangeLog("classpath:/liquibase/index.xml");

        return springLiquibase;
    }
}
