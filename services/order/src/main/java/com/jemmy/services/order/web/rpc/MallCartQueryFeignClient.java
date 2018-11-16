/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MallCartQueryFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.order.web.rpc;

import com.jemmy.apis.omc.model.vo.CartVo;
import com.jemmy.apis.omc.service.OmcCartQueryFeignApi;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.services.order.service.OmcCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The class Mall cart query feign client.
 *
 * @author paascloud.net@gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - MallCartQueryFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MallCartQueryFeignClient extends BaseController implements OmcCartQueryFeignApi {

	@Resource
	private OmcCartService omcCartService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "获取购物车信息")
	public MvcResult<CartVo> getCartVo(@RequestParam("userId") Long userId) {
		logger.info("getCartVo - 获取购物车信息. userId={}", userId);
		CartVo cartVo = omcCartService.getCarVo(userId);
		return MvcResultBuilder.wrap(MvcResult.SUCCESS_CODE, MvcResult.SUCCESS_MESSAGE, cartVo);
	}
}
