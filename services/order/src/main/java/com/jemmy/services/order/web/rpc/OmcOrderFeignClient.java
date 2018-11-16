/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.order.web.rpc;

import com.jemmy.apis.omc.model.dto.OrderDto;
import com.jemmy.apis.omc.service.OmcOrderFeignApi;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.services.order.model.domain.OmcOrder;
import com.jemmy.services.order.service.OmcOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The class Omc order feign client.
 *
 * @author paascloud.net@gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - OmcOrderFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcOrderFeignClient extends BaseController implements OmcOrderFeignApi {
	@Resource
	private OmcOrderService omcOrderService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "更新订单信息")
	public MvcResult updateOrderById(@RequestBody OrderDto orderDto) {
		logger.info("updateOrderById - 更新订单信息. orderDto={}", orderDto);
		ModelMapper modelMapper = new ModelMapper();
		OmcOrder omcOrder = modelMapper.map(orderDto, OmcOrder.class);
		int updateResult = omcOrderService.update(omcOrder);
		return handleResult(updateResult);

	}
}
