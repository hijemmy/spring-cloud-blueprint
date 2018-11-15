package com.jemmy.services.opc;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.jemmy.common.core.mybatis.MyMapper;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by pc on 2018/11/10.
 */
@SpringCloudApplication
@EnableFeignClients(basePackages = {"com.jemmy.apis"})
@EnableOAuth2Client
@EnableSwagger2
@MapperScan(basePackages = {"com.jemmy.services.opc.mapper","com.jemmy.apis.rmq.mapper"},markerInterface = MyMapper.class)
@ComponentScan({"com.jemmy.common.config","com.jemmy.common.zk","com.jemmy.common.core.config","com.jemmy.common.core.support","com.jemmy.apis","com.jemmy.services.opc"})
public class OpcApplication {

    public static void main(String... args){
        SpringApplication.run(OpcApplication.class, args);
    }

    /**
     * Get kaptcha bean default kaptcha.
     *
     * @return the default kaptcha
     */
    @Bean(name = "captchaProducer")
    public DefaultKaptcha getKaptchaBean() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.image.width", "125");
        properties.setProperty("kaptcha.image.height", "45");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

    @Bean
    public SpringLiquibase springLiquibase(DataSource dataSource) {

        SpringLiquibase springLiquibase = new SpringLiquibase();

        springLiquibase.setDataSource(dataSource);
        springLiquibase.setChangeLog("classpath:/liquibase/index.xml");

        return springLiquibase;
    }
}
