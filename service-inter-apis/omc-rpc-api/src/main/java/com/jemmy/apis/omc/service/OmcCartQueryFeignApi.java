/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcCartQueryFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.apis.omc.service;

import com.jemmy.apis.omc.model.vo.CartVo;
import com.jemmy.apis.omc.service.hystrix.OmcCartFeignHystrix;
import com.jemmy.apis.omc.service.hystrix.OmcCartQueryFeignHystrix;
import com.jemmy.common.security.feign.OAuth2FeignAutoConfiguration;
import com.jemmy.common.util.wrapper.Wrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.jemmy.common.base.constant.GlobalConstant.MICRO_SERVICE_NAME_ORDER;

/**
 * The interface Omc cart query feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = MICRO_SERVICE_NAME_ORDER, configuration = OAuth2FeignAutoConfiguration.class, fallback = OmcCartQueryFeignHystrix.class)
public interface OmcCartQueryFeignApi {

	/**
	 * Gets cart vo.
	 *
	 * @param userId the user id
	 *
	 * @return the cart vo
	 */
	@PostMapping(value = "/api/cart/getCarVo")
	Wrapper<CartVo> getCartVo(@RequestParam("userId") Long userId);
}
