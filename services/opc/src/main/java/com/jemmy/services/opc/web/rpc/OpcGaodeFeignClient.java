/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcGaodeFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.opc.web.rpc;

import com.jemmy.apis.opc.model.dto.gaode.GaodeLocation;
import com.jemmy.apis.opc.service.OpcGaodeFeignApi;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.services.opc.utils.GaoDeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The class Opc attachment feign client.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@Api(value = "API - OpcGaodeFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OpcGaodeFeignClient extends BaseController implements OpcGaodeFeignApi {

	@Override
	@ApiOperation(httpMethod = "POST", value = "根据IP获取位置信息")
	public MvcResult<GaodeLocation> getLocationByIpAddr(@RequestParam("ipAddr") String ipAddr) {
		String temp = "127.0.";
		String temp2 = "192.168.";
		if (ipAddr.startsWith(temp) || ipAddr.startsWith(temp2)) {
			ipAddr = "111.199.188.14";
		}
		return MvcResultBuilder.ok(GaoDeUtil.getCityByIpAddr(ipAddr));
	}
}
