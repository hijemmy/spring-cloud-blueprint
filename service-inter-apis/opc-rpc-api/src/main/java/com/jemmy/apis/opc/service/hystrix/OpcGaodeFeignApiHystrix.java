/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcGaodeFeignApiHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.apis.opc.service.hystrix;

import com.jemmy.apis.opc.model.dto.gaode.GaodeLocation;
import com.jemmy.apis.opc.service.OpcGaodeFeignApi;
import com.jemmy.common.util.wrapper.MvcResult;
import org.springframework.stereotype.Component;

/**
 * The class Opc oss feign api hystrix.
 *
 * @author paascloud.net@gmail.com
 */
@Component
public class OpcGaodeFeignApiHystrix implements OpcGaodeFeignApi {

	@Override
	public MvcResult<GaodeLocation> getLocationByIpAddr(final String ipAddr) {
		return null;
	}
}
