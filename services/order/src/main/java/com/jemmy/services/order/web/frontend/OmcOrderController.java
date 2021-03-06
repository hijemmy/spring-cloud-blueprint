/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.order.web.frontend;

import com.github.pagehelper.PageInfo;
import com.jemmy.apis.omc.model.dto.OrderPageQuery;
import com.jemmy.apis.omc.model.vo.OrderProductVo;
import com.jemmy.apis.omc.model.vo.OrderVo;
import com.jemmy.common.base.dto.BaseQuery;
import com.jemmy.common.base.dto.LoginAuthDto;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.services.order.service.OmcCartService;
import com.jemmy.services.order.service.OmcOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * The class Omc order controller.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@RequestMapping(value = "/order")
@Api(value = "WEB - OmcOrderController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcOrderController extends BaseController {

	@Resource
	private OmcOrderService omcOrderService;
	@Resource
	private OmcCartService omcCartService;

	/**
	 * 获取购物车数量.
	 *
	 * @return the cart count
	 */
	@PostMapping(value = "getCartCount")
	public MvcResult<Integer> getCartCount() {
		return MvcResultBuilder.ok(0);
	}

	/**
	 * 获取购物车商品数量.
	 *
	 * @return the order cart product
	 */
	@PostMapping("/getOrderCartProduct")
	@ApiOperation(httpMethod = "POST", value = "获取购物车商品数量")
	public MvcResult getOrderCartProduct() {
		logger.info("getOrderCartProduct - 获取购物车商品数量");
		OrderProductVo orderCartProduct = omcCartService.getOrderCartProduct(getLoginAuthDto().getUserId());
		return MvcResultBuilder.ok(orderCartProduct);
	}

	/**
	 * 创建订单.
	 *
	 * @param shippingId the shipping id
	 *
	 * @return the wrapper
	 */
	@PostMapping("createOrderDoc/{shippingId}")
	@ApiOperation(httpMethod = "POST", value = "创建订单")
	public MvcResult createOrderDoc(@PathVariable Long shippingId) {
		logger.info("createOrderDoc - 创建订单. shippingId={}", shippingId);
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		logger.info("操作人信息. loginAuthDto={}", loginAuthDto);

		OrderVo orderDoc = omcOrderService.createOrderDoc(loginAuthDto, shippingId);
		return MvcResultBuilder.ok(orderDoc);
	}


	/**
	 * 取消订单.
	 *
	 * @param orderNo the order no
	 *
	 * @return the wrapper
	 */
	@PostMapping("cancelOrderDoc/{orderNo}")
	@ApiOperation(httpMethod = "POST", value = "取消订单")
	public MvcResult cancelOrderDoc(@PathVariable String orderNo) {
		logger.info("cancelOrderDoc - 取消订单. orderNo={}", orderNo);
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		logger.info("操作人信息. loginAuthDto={}", loginAuthDto);

		int result = omcOrderService.cancelOrderDoc(loginAuthDto, orderNo);
		return handleResult(result);
	}

	/**
	 * 查询订单详情.
	 *
	 * @param orderNo the order no
	 *
	 * @return the wrapper
	 */
	@PostMapping("queryUserOrderDetailList/{orderNo}")
	@ApiOperation(httpMethod = "POST", value = "查询订单详情")
	public MvcResult queryUserOrderDetailList(@PathVariable String orderNo) {
		logger.info("queryUserOrderDetailList - 查询用户订单明细. orderNo={}", orderNo);

		Long userId = getLoginAuthDto().getUserId();
		logger.info("操作人信息. userId={}", userId);

		OrderVo orderVo = omcOrderService.getOrderDetail(userId, orderNo);
		return MvcResultBuilder.ok(orderVo);
	}

	@PostMapping("queryUserOrderDetail/{orderNo}")
	@ApiOperation(httpMethod = "POST", value = "查询订单详情")
	public MvcResult queryUserOrderDetail(@PathVariable String orderNo) {
		logger.info("queryUserOrderDetail - 查询订单明细. orderNo={}", orderNo);

		OrderVo orderVo = omcOrderService.getOrderDetail(orderNo);
		return MvcResultBuilder.ok(orderVo);
	}

	/**
	 * Query user order list with page wrapper.
	 *
	 * @param baseQuery the base query
	 *
	 * @return the wrapper
	 */
	@PostMapping("queryUserOrderListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询用户订单列表")
	public MvcResult queryUserOrderListWithPage(@RequestBody BaseQuery baseQuery) {
		logger.info("queryUserOrderListWithPage - 查询用户订单集合. baseQuery={}", baseQuery);

		Long userId = getLoginAuthDto().getUserId();
		logger.info("操作人信息. userId={}", userId);

		PageInfo pageInfo = omcOrderService.queryUserOrderListWithPage(userId, baseQuery);
		return MvcResultBuilder.ok(pageInfo);
	}

	@PostMapping("queryOrderListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询用户订单列表")
	public MvcResult queryOrderListWithPage(@RequestBody OrderPageQuery orderPageQuery) {
		logger.info("queryOrderListWithPage - 查询订单集合. orderPageQuery={}", orderPageQuery);
		PageInfo pageInfo = omcOrderService.queryOrderListWithPage(orderPageQuery);
		return MvcResultBuilder.ok(pageInfo);
	}

	/**
	 * 查询订单状态.
	 *
	 * @param orderNo the order no
	 *
	 * @return the wrapper
	 */
	@PostMapping("queryOrderPayStatus/{orderNo}")
	@ApiOperation(httpMethod = "POST", value = "查询订单状态")
	public MvcResult<Boolean> queryOrderPayStatus(@PathVariable String orderNo) {
		logger.info("queryOrderPayStatus - 查询订单状态. orderNo={}", orderNo);
		boolean result = omcOrderService.queryOrderPayStatus(getLoginAuthDto().getUserId(), orderNo);
		return MvcResultBuilder.ok(result);
	}

}
