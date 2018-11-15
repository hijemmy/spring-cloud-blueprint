/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductQueryFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.apis.product.service;

import com.jemmy.apis.product.model.dto.ProductDto;
import com.jemmy.apis.product.model.vo.ProductDetailVo;
import com.jemmy.apis.product.service.hystrix.MdcProductQueryFeignHystrix;
import com.jemmy.common.security.feign.OAuth2FeignAutoConfiguration;
import com.jemmy.common.util.annotation.NoNeedAccessAuthentication;
import com.jemmy.common.util.wrapper.Wrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static com.jemmy.common.base.constant.GlobalConstant.MICRO_SERVICE_NAME_PRODUCT;

/**
 * The interface Mdc product query feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = MICRO_SERVICE_NAME_PRODUCT, configuration = OAuth2FeignAutoConfiguration.class, fallback = MdcProductQueryFeignHystrix.class)
public interface MdcProductQueryFeignApi {

	/**
	 * 查询商品详情信息.
	 *
	 * @param productId the product id
	 *
	 * @return the product detail
	 */
	@PostMapping(value = "/api/product/getProductDetail/{productId}")
	@NoNeedAccessAuthentication
	Wrapper<ProductDetailVo> getProductDetail(@PathVariable("productId") Long productId);

	/**
	 * Select by id wrapper.
	 *
	 * @param productId the product id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/product/selectById/{productId}")
	@NoNeedAccessAuthentication
	Wrapper<ProductDto> selectById(@PathVariable("productId") Long productId);
}
