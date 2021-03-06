/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcAddressQueryFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.product.web.rpc;

import com.jemmy.apis.product.model.dto.AddressDTO;
import com.jemmy.apis.product.service.MdcAddressQueryFeignApi;
import com.jemmy.common.util.PublicUtil;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.services.product.model.domain.MdcAddress;
import com.jemmy.services.product.service.MdcAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The class Mdc product query feign client.
 *
 * @author paascloud.net @gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - MdcAddressQueryFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcAddressQueryFeignClient extends BaseController implements MdcAddressQueryFeignApi {

	@Resource
	private MdcAddressService mdcAddressService;

	/**
	 * 根据ID获取地址信息.
	 *
	 * @param addressId the address id
	 *
	 * @return the by id
	 */
	@Override
	@ApiOperation(httpMethod = "POST", value = "根据ID获取地址信息")
	public MvcResult<AddressDTO> getById(@PathVariable("addressId") Long addressId) {
		logger.info("根据ID获取地址信息 addressId={}", addressId);
		AddressDTO addressDTO = null;
		MdcAddress mdcAddress = mdcAddressService.selectByKey(addressId);
		if (PublicUtil.isNotEmpty(mdcAddress)) {
			addressDTO = new AddressDTO();
			BeanUtils.copyProperties(mdcAddress, addressDTO);
		}
		return MvcResultBuilder.ok(addressDTO);
	}
}
