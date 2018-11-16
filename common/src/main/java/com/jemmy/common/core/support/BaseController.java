/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：BaseController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.common.core.support;

import com.jemmy.common.base.constant.GlobalConstant;
import com.jemmy.common.base.dto.LoginAuthDto;
import com.jemmy.common.base.enums.ErrorCodeEnum;
import com.jemmy.common.base.exception.BusinessException;
import com.jemmy.common.util.PublicUtil;
import com.jemmy.common.util.ThreadLocalMap;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.common.zk.generator.IncrementIdGenerator;
import com.jemmy.common.zk.generator.UniqueIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class Base controller.
 *
 * @author paascloud.net@gmail.com
 */
public class BaseController {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Gets login auth dto.
	 *
	 * @return the login auth dto
	 */
	protected LoginAuthDto getLoginAuthDto() {
		LoginAuthDto loginAuthDto = (LoginAuthDto) ThreadLocalMap.get(GlobalConstant.Sys.TOKEN_AUTH_DTO);
		if (PublicUtil.isEmpty(loginAuthDto)) {
			throw new BusinessException(ErrorCodeEnum.UAC10011041);
		}
		return loginAuthDto;
	}

	/**
	 * Handle result wrapper.
	 *
	 * @param <T>    the type parameter
	 * @param result the result
	 *
	 * @return the wrapper
	 */
	protected <T> MvcResult<T> handleResult(T result) {
		boolean flag = isFlag(result);

		if (flag) {
			return MvcResultBuilder.wrap(MvcResult.SUCCESS_CODE, "操作成功", result);
		} else {
			return MvcResultBuilder.wrap(MvcResult.ERROR_CODE, "操作失败", result);
		}
	}

	/**
	 * Handle result wrapper.
	 *
	 * @param <E>      the type parameter
	 * @param result   the result
	 * @param errorMsg the error msg
	 *
	 * @return the wrapper
	 */
	protected <E> MvcResult<E> handleResult(E result, String errorMsg) {
		boolean flag = isFlag(result);

		if (flag) {
			return MvcResultBuilder.wrap(MvcResult.SUCCESS_CODE, "操作成功", result);
		} else {
			return MvcResultBuilder.wrap(MvcResult.ERROR_CODE, errorMsg, result);
		}
	}

	private boolean isFlag(Object result) {
		boolean flag;
		if (result instanceof Integer) {
			flag = (Integer) result > 0;
		} else if (result instanceof Boolean) {
			flag = (Boolean) result;
		} else {
			flag = PublicUtil.isNotEmpty(result);
		}
		return flag;
	}

	protected long generateId() {
		return UniqueIdGenerator.getInstance(IncrementIdGenerator.getServiceId()).nextId();
	}

}
  