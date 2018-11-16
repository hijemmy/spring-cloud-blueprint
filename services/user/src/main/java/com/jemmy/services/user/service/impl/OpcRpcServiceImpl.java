/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcRpcServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.user.service.impl;

import com.jemmy.apis.opc.model.dto.gaode.GaodeLocation;
import com.jemmy.apis.opc.service.OpcGaodeFeignApi;
import com.jemmy.apis.product.exceptions.MdcBizException;
import com.jemmy.common.base.constant.GlobalConstant;
import com.jemmy.common.base.enums.ErrorCodeEnum;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.services.user.service.OpcRpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * The class Opc rpc service.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Service
public class OpcRpcServiceImpl implements OpcRpcService {
	@Resource
	private OpcGaodeFeignApi opcGaodeFeignApi;

	@Override
	public String getLocationById(String addressId) {
		try {
			MvcResult<GaodeLocation> mvcResult = opcGaodeFeignApi.getLocationByIpAddr(addressId);
			if (mvcResult == null) {
				throw new MdcBizException(ErrorCodeEnum.GL99990002);
			}
			if (mvcResult.error()) {
				throw new MdcBizException(ErrorCodeEnum.MDC10021002);
			}

			GaodeLocation result = mvcResult.getResult();

			assert result != null;
			return result.getProvince().contains("市") ? result.getCity() : result.getProvince() + GlobalConstant.Symbol.SHORT_LINE + result.getCity();
		} catch (Exception e) {
			log.error("getLocationById={}", e.getMessage(), e);
		}
		return null;
	}
}
