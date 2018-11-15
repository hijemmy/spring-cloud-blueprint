/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：CoreConfiguration.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.common.core.config;


import com.jemmy.common.core.interceptor.SqlLogInterceptor;
import com.jemmy.common.core.interceptor.TokenInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.jemmy.common.base.constant.GlobalConstant.ROOT_PREFIX;

/**
 * 加载LWR规则.
 *
 * @author paascloud.net@gmail.com
 */
@Configuration
public class CoreConfiguration {
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public SqlLogInterceptor sqlLogInterceptor() {
		return new SqlLogInterceptor();
	}


	@Bean
	@ConditionalOnMissingBean(HandlerInterceptor.class)
	@ConditionalOnProperty(prefix = ROOT_PREFIX+".token.interceptor", name = "enable", havingValue = "true")
	public TokenInterceptor tokenInterceptor() {
		return new TokenInterceptor();
	}
}
