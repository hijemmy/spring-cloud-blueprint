/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MvcResultBuilder.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.common.util.wrapper;

import org.apache.commons.lang3.StringUtils;

/**
 * The class Wrap mapper.
 *
 * @author paascloud.net@gmail.com
 */
public class MvcResultBuilder {

	/**
	 * Instantiates a new wrap mapper.
	 */
	private MvcResultBuilder() {
	}

	/**
	 * Wrap.
	 *
	 * @param <E>     the element type
	 * @param code    the code
	 * @param message the message
	 * @param o       the o
	 *
	 * @return the wrapper
	 */
	public static <E> MvcResult<E> wrap(int code, String message, E o) {
		return new MvcResult<>(code, message, o);
	}

	/**
	 * Wrap.
	 *
	 * @param <E>     the element type
	 * @param code    the code
	 * @param message the message
	 *
	 * @return the wrapper
	 */
	public static <E> MvcResult<E> wrap(int code, String message) {
		return wrap(code, message, null);
	}

	/**
	 * Wrap.
	 *
	 * @param <E>  the element type
	 * @param code the code
	 *
	 * @return the wrapper
	 */
	public static <E> MvcResult<E> wrap(int code) {
		return wrap(code, null);
	}

	/**
	 * Wrap.
	 *
	 * @param <E> the element type
	 * @param e   the e
	 *
	 * @return the wrapper
	 */
	public static <E> MvcResult<E> wrap(Exception e) {
		return new MvcResult<>(MvcResult.ERROR_CODE, e.getMessage());
	}

	/**
	 * Un mvcResult.
	 *
	 * @param <E>     the element type
	 * @param mvcResult the mvcResult
	 *
	 * @return the e
	 */
	public static <E> E unWrap(MvcResult<E> mvcResult) {
		return mvcResult.getResult();
	}

	/**
	 * Wrap ERROR. code=100
	 *
	 * @param <E> the element type
	 *
	 * @return the wrapper
	 */
	public static <E> MvcResult<E> illegalArgument() {
		return wrap(MvcResult.ILLEGAL_ARGUMENT_CODE_, MvcResult.ILLEGAL_ARGUMENT_MESSAGE);
	}

	/**
	 * Wrap ERROR. code=500
	 *
	 * @param <E> the element type
	 *
	 * @return the wrapper
	 */
	public static <E> MvcResult<E> error() {
		return wrap(MvcResult.ERROR_CODE, MvcResult.ERROR_MESSAGE);
	}


	/**
	 * Error wrapper.
	 *
	 * @param <E>     the type parameter
	 * @param message the message
	 *
	 * @return the wrapper
	 */
	public static <E> MvcResult<E> error(String message) {
		return wrap(MvcResult.ERROR_CODE, StringUtils.isBlank(message) ? MvcResult.ERROR_MESSAGE : message);
	}

	/**
	 * Wrap SUCCESS. code=200
	 *
	 * @param <E> the element type
	 *
	 * @return the wrapper
	 */
	public static <E> MvcResult<E> ok() {
		return new MvcResult<>();
	}

	/**
	 * Ok wrapper.
	 *
	 * @param <E> the type parameter
	 * @param o   the o
	 *
	 * @return the wrapper
	 */
	public static <E> MvcResult<E> ok(E o) {
		return new MvcResult<>(MvcResult.SUCCESS_CODE, MvcResult.SUCCESS_MESSAGE, o);
	}
}
