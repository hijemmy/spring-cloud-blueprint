/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderDetailFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.apis.omc.service;

import com.jemmy.apis.omc.service.hystrix.OmcOrderDetailFeignHystrix;
import com.jemmy.common.security.feign.OAuth2FeignAutoConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;

import static com.jemmy.common.base.constant.GlobalConstant.MICRO_SERVICE_NAME_ORDER;

/**
 * The interface Omc order detail feign api.
 *
 * @author paascloud.net@gmail.com
 */
@FeignClient(value = MICRO_SERVICE_NAME_ORDER, configuration = OAuth2FeignAutoConfiguration.class, fallback = OmcOrderDetailFeignHystrix.class)
public interface OmcOrderDetailFeignApi {
}
