/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductCategoryQueryFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.apis.product.service;

import com.github.pagehelper.PageInfo;
import com.jemmy.apis.product.model.dto.ProductCategoryDto;
import com.jemmy.apis.product.model.dto.ProductReqDto;
import com.jemmy.apis.product.service.hystrix.MdcProductCategoryQueryFeignHystrix;
import com.jemmy.common.security.feign.OAuth2FeignAutoConfiguration;
import com.jemmy.common.util.annotation.NoNeedAccessAuthentication;
import com.jemmy.common.util.wrapper.MvcResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import static com.jemmy.common.base.constant.GlobalConstant.MICRO_SERVICE_NAME_PRODUCT;

/**
 * The interface Mdc product category query feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = MICRO_SERVICE_NAME_PRODUCT, configuration = OAuth2FeignAutoConfiguration.class, fallback = MdcProductCategoryQueryFeignHystrix.class)
public interface MdcProductCategoryQueryFeignApi {

	/**
	 * 查询分类信息.
	 *
	 * @param pid the pid
	 *
	 * @return the product category data
	 */
	@PostMapping(value = "/api/productCategory/getProductCategoryDtoByPid/{pid}")
	@NoNeedAccessAuthentication
    MvcResult<List<ProductCategoryDto>> getProductCategoryData(@PathVariable("pid") Long pid);

	/**
	 * 查询商品列表.
	 *
	 * @param productReqDto the product req dto
	 *
	 * @return the product list
	 */
	@PostMapping(value = "/api/product/getProductList")
	@NoNeedAccessAuthentication
    MvcResult<PageInfo> getProductList(@RequestBody ProductReqDto productReqDto);

}
