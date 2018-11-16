/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcCartFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.apis.omc.service.hystrix;


import com.jemmy.apis.omc.model.vo.CartProductVo;
import com.jemmy.apis.omc.service.OmcCartFeignApi;
import com.jemmy.common.util.wrapper.MvcResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The class Omc cart feign hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class OmcCartFeignHystrix implements OmcCartFeignApi {
	@Override
	public MvcResult updateCartList(final List<CartProductVo> cartProductVoList) {
		return null;
	}

	@Override
	public MvcResult addProduct(final Long userId, final Long productId, final Integer count) {
		return null;
	}

	@Override
	public MvcResult updateProduct(final Long userId, final Long productId, final Integer count) {
		return null;
	}

	@Override
	public MvcResult deleteProduct(final Long userId, final String productIds) {
		return null;
	}

	@Override
	public MvcResult selectOrUnSelect(final Long userId, final Long productId, final Integer checked) {
		return null;
	}
}
