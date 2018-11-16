/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.apis.product.service.hystrix;


import com.jemmy.apis.product.model.dto.ProductDto;
import com.jemmy.apis.product.service.MdcProductFeignApi;
import com.jemmy.common.util.wrapper.MvcResult;
import org.springframework.stereotype.Component;

/**
 * The class Mdc product feign hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class MdcProductFeignHystrix implements MdcProductFeignApi {

	@Override
	public MvcResult<Integer> updateProductStockById(final ProductDto productDto) {
		return null;
	}

	@Override
	public MvcResult<String> getMainImage(final Long productId) {
		return null;
	}
}
