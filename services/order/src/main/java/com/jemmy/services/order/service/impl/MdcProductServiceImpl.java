/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.order.service.impl;


import com.google.common.base.Preconditions;
import com.jemmy.apis.product.service.MdcProductFeignApi;
import com.jemmy.apis.product.service.MdcProductQueryFeignApi;
import com.jemmy.common.base.enums.ErrorCodeEnum;
import com.jemmy.apis.product.exceptions.MdcBizException;
import com.jemmy.apis.product.model.dto.ProductDto;
import com.jemmy.services.order.service.MdcProductService;
import com.jemmy.apis.product.model.vo.ProductDetailVo;
import com.jemmy.common.util.wrapper.MvcResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * The class Mdc product service.
 *
 * @author paascloud.net@gmail.com
 */
@Slf4j
@Service
public class MdcProductServiceImpl implements MdcProductService {
	@Resource
	private MdcProductQueryFeignApi mdcProductQueryFeignApi;
	@Resource
	private MdcProductFeignApi mdcProductFeignApi;

	@Override
	public ProductDto selectById(Long productId) {
		log.info("查询商品信息. productId={}", productId);
		Preconditions.checkArgument(productId != null, ErrorCodeEnum.MDC10021021.msg());
		MvcResult<ProductDto> productDtoMvcResult = mdcProductQueryFeignApi.selectById(productId);

		if (productDtoMvcResult == null) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021003);
		}
		if (productDtoMvcResult.error()) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021004, productId);
		}
		return productDtoMvcResult.getResult();

	}

	@Override
	public ProductDetailVo getProductDetail(Long productId) {
		log.info("获取商品详情. productId={}", productId);
		Preconditions.checkArgument(productId != null, ErrorCodeEnum.MDC10021021.msg());

		MvcResult<ProductDetailVo> mvcResult = mdcProductQueryFeignApi.getProductDetail(productId);

		if (mvcResult == null) {
			throw new MdcBizException(ErrorCodeEnum.GL99990002);
		}
		if (mvcResult.error()) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021004, productId);
		}
		return mvcResult.getResult();
	}

	@Override
	public int updateProductStockById(ProductDto productDto) {
		Preconditions.checkArgument(productDto.getId() != null, ErrorCodeEnum.MDC10021021.msg());
		MvcResult<Integer> mvcResult = mdcProductFeignApi.updateProductStockById(productDto);
		if (mvcResult == null) {
			throw new MdcBizException(ErrorCodeEnum.GL99990002);
		}
		if (mvcResult.error()) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021022, productDto.getId());
		}
		return mvcResult.getResult();
	}

	@Override
	public String getMainImage(final Long productId) {
		MvcResult<String> mvcResult = mdcProductFeignApi.getMainImage(productId);
		if (mvcResult == null) {
			throw new MdcBizException(ErrorCodeEnum.GL99990002);
		}
		return mvcResult.getResult();
	}
}
