/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductCategoryQueryFeignHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.apis.product.service.hystrix;

import com.github.pagehelper.PageInfo;
import com.jemmy.apis.product.model.dto.ProductCategoryDto;
import com.jemmy.apis.product.model.dto.ProductReqDto;
import com.jemmy.apis.product.service.MdcProductCategoryQueryFeignApi;
import com.jemmy.common.util.wrapper.MvcResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The class Mdc product category query feign hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class MdcProductCategoryQueryFeignHystrix implements MdcProductCategoryQueryFeignApi {
	@Override
	public MvcResult<List<ProductCategoryDto>> getProductCategoryData(final Long pid) {
		return null;
	}

	@Override
	public MvcResult<PageInfo> getProductList(final ProductReqDto productReqDto) {
		return null;
	}
}
