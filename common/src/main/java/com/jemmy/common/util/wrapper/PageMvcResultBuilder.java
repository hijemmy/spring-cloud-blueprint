/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PageMvcResultBuilder.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.common.util.wrapper;


import com.jemmy.common.util.page.PageUtil;

/**
 * The class Page wrap mapper.
 *
 * @author paascloud.net@gmail.com
 */
public class PageMvcResultBuilder {

	/**
	 * Instantiates a new page wrap mapper.
	 */
	private PageMvcResultBuilder() {
	}

	private static <E> PageMvcResult<E> wrap(int code, String message, E o, PageUtil pageUtil) {
		return new PageMvcResult<E>(code, message, o, pageUtil);
	}

	/**
	 * Wrap data with default code=200.
	 *
	 * @param <E>      the type parameter
	 * @param o        the o
	 * @param pageUtil the page util
	 *
	 * @return the page wrapper
	 */
	public static <E> PageMvcResult<E> wrap(E o, PageUtil pageUtil) {
		return wrap(MvcResult.SUCCESS_CODE, MvcResult.SUCCESS_MESSAGE, o, pageUtil);
	}

	/**
	 * Wrap.
	 *
	 * @param <E>     the type parameter
	 * @param code    the code
	 * @param message the message
	 *
	 * @return the page wrapper
	 */
	public static <E> PageMvcResult<E> wrap(int code, String message) {
		return wrap(code, message, null, null);
	}

	/**
	 * Wrap.
	 *
	 * @param <E>  the type parameter
	 * @param code the code
	 *
	 * @return the page wrapper
	 */
	public static <E> PageMvcResult<E> wrap(int code) {
		return wrap(code, null, null, null);
	}

	/**
	 * Wrap.
	 *
	 * @param <E> the type parameter
	 * @param e   the e
	 *
	 * @return the page wrapper
	 */
	public static <E> PageMvcResult<E> wrap(Exception e) {
		return new PageMvcResult<E>(MvcResult.ERROR_CODE, e.getMessage(), null, null);
	}

	/**
	 * Un wrapper.
	 *
	 * @param <E>     the type parameter
	 * @param wrapper the wrapper
	 *
	 * @return the e
	 */
	public static <E> E unWrap(PageMvcResult<E> wrapper) {
		return wrapper.getResult();
	}

	/**
	 * Wrap ERROR. code=100
	 *
	 * @param <E> the type parameter
	 *
	 * @return the page wrapper
	 */
	public static <E> PageMvcResult<E> illegalArgument() {
		return wrap(MvcResult.ILLEGAL_ARGUMENT_CODE_, MvcResult.ILLEGAL_ARGUMENT_MESSAGE, null, null);
	}

	/**
	 * Wrap ERROR. code=500
	 *
	 * @param <E> the type parameter
	 *
	 * @return the page wrapper
	 */
	public static <E> PageMvcResult<E> error() {
		return wrap(MvcResult.ERROR_CODE, MvcResult.ERROR_MESSAGE, null, null);
	}

	/**
	 * Wrap SUCCESS. code=200
	 *
	 * @param <E> the type parameter
	 *
	 * @return the page wrapper
	 */
	public static <E> PageMvcResult<E> ok() {
		return new PageMvcResult<E>();
	}
}
