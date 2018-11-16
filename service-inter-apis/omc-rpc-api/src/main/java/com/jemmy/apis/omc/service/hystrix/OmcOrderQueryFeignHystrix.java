/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderQueryFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.apis.omc.service.hystrix;

import com.jemmy.apis.omc.model.dto.OrderDto;
import com.jemmy.apis.omc.service.OmcOrderQueryFeignApi;
import com.jemmy.common.util.wrapper.MvcResult;
import org.springframework.stereotype.Component;

/**
 * The class Omc order query feign hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class OmcOrderQueryFeignHystrix implements OmcOrderQueryFeignApi {


	@Override
	public MvcResult<OrderDto> queryByOrderNo(final String orderNo) {
		return null;
	}

	@Override
	public MvcResult<OrderDto> queryByUserIdAndOrderNo(final Long userId, final String orderNo) {
		return null;
	}
}
