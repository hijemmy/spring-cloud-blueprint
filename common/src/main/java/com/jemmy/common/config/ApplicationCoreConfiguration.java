package com.jemmy.common.config;

import com.jemmy.common.config.properties.ApplicationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 加载程序配置参数
 * @author Jemmy
 */
@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationCoreConfiguration {
}
