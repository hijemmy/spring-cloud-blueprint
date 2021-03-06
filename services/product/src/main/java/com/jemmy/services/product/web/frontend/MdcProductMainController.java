/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.product.web.frontend;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jemmy.common.core.annotation.LogAnnotation;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.services.product.model.domain.MdcProduct;
import com.jemmy.services.product.model.dto.MdcEditProductDto;
import com.jemmy.services.product.model.vo.ProductVo;
import com.jemmy.services.product.service.MdcProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Mdc dict main controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MdcProductMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcProductMainController extends BaseController {

	@Resource
	private MdcProductService mdcProductService;

	/**
	 * 分页查询商品列表.
	 *
	 * @param mdcProduct the mdc product
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryProductListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询商品列表")
	public MvcResult<PageInfo<ProductVo>> queryProductListWithPage(@ApiParam(name = "mdcProduct", value = "商品信息") @RequestBody MdcProduct mdcProduct) {

		logger.info("分页查询商品列表, mdcProduct={}", mdcProduct);
		PageHelper.startPage(mdcProduct.getPageNum(), mdcProduct.getPageSize());
		mdcProduct.setOrderBy("update_time desc");
		List<ProductVo> roleVoList = mdcProductService.queryProductListWithPage(mdcProduct);
		return MvcResultBuilder.ok(new PageInfo<>(roleVoList));
	}

	/**
	 * 商品详情.
	 */
	@PostMapping(value = "/getById/{id}")
	@ApiOperation(httpMethod = "POST", value = "分页查询商品列表")
	public MvcResult<ProductVo> getById(@PathVariable Long id) {
		logger.info("查询商品详情, id={}", id);
		ProductVo productVo = mdcProductService.getProductVo(id);
		return MvcResultBuilder.ok(productVo);
	}

	@PostMapping(value = "/save")
	@ApiOperation(httpMethod = "POST", value = "编辑商品")
	@LogAnnotation
	public MvcResult saveCategory(@RequestBody MdcEditProductDto mdcCategoryAddDto) {
		logger.info("编辑商品. mdcCategoryAddDto={}", mdcCategoryAddDto);
		mdcProductService.saveProduct(mdcCategoryAddDto, getLoginAuthDto());
		return MvcResultBuilder.ok();
	}

	@PostMapping(value = "/deleteProductById/{id}")
	@ApiOperation(httpMethod = "POST", value = "删除商品信息")
	@LogAnnotation
	public MvcResult<ProductVo> deleteProductById(@PathVariable Long id) {
		logger.info("删除商品信息, id={}", id);
		mdcProductService.deleteProductById(id);
		return MvcResultBuilder.ok();
	}
}
