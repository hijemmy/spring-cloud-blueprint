/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderDetailQueryFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.apis.omc.service.hystrix;

import com.jemmy.apis.omc.model.dto.OrderDetailDto;
import com.jemmy.apis.omc.service.OmcOrderDetailQueryFeignApi;
import com.jemmy.common.util.wrapper.MvcResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The class Omc order detail query feign hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class OmcOrderDetailQueryFeignHystrix implements OmcOrderDetailQueryFeignApi {

	@Override
	public MvcResult<List<OrderDetailDto>> getListByOrderNoUserId(final String orderNo, final Long userId) {
		return null;
	}
}
