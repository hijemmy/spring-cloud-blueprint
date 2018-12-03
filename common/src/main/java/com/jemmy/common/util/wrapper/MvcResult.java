/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MvcResult.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.common.util.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

import static com.jemmy.common.base.enums.ErrorCodeEnum.GL99990100;
import static com.jemmy.common.base.enums.ErrorCodeEnum.GL99990500;


/**
 * The class MvcResult.
 *
 * @param <T> the type parameter @author paascloud.net@gmail.com
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MvcResult<T> implements Serializable {

	/**
	 * 序列化标识
	 */
	private static final long serialVersionUID = 4893280118017319089L;

	/**
	 * 成功码.
	 */
	public static final int SUCCESS_CODE = 200;

	/**
	 * 成功信息.
	 */
	public static final String SUCCESS_MESSAGE = "操作成功";

	/**
	 * 错误码.
	 */
	public static final int ERROR_CODE = GL99990500.code();

	/**
	 * 错误信息.
	 */
	public static final String ERROR_MESSAGE = GL99990500.msg();

	/**
	 * 错误码：参数非法
	 */
	public static final int ILLEGAL_ARGUMENT_CODE_ = GL99990100.code();

	/**
	 * 错误信息：参数非法
	 */
	public static final String ILLEGAL_ARGUMENT_MESSAGE = GL99990100.msg();

	/**
	 * 编号.
	 */
	@ApiModelProperty(example = "200")
	private int code;

	/**
	 * 信息.
	 */
	@ApiModelProperty(example = SUCCESS_MESSAGE)
	private String message;

	/**
	 * 结果数据
	 */
	private T result;

	/**
	 * Instantiates a new wrapper. default code=200
	 */
	MvcResult() {
		this(SUCCESS_CODE, SUCCESS_MESSAGE);
	}

	/**
	 * Instantiates a new wrapper.
	 *
	 * @param code    the code
	 * @param message the message
	 */
	MvcResult(int code, String message) {
		this(code, message, null);
	}

	/**
	 * Instantiates a new wrapper.
	 *
	 * @param code    the code
	 * @param message the message
	 * @param result  the result
	 */
	MvcResult(int code, String message, T result) {
		super();
		this.code(code).message(message).result(result);
	}

	/**
	 * Sets the 编号 , 返回自身的引用.
	 *
	 * @param code the new 编号
	 *
	 * @return the wrapper
	 */
	private MvcResult<T> code(int code) {
		this.setCode(code);
		return this;
	}

	/**
	 * Sets the 信息 , 返回自身的引用.
	 *
	 * @param message the new 信息
	 *
	 * @return the wrapper
	 */
	private MvcResult<T> message(String message) {
		this.setMessage(message);
		return this;
	}

	/**
	 * Sets the 结果数据 , 返回自身的引用.
	 *
	 * @param result the new 结果数据
	 *
	 * @return the wrapper
	 */
	public MvcResult<T> result(T result) {
		this.setResult(result);
		return this;
	}

	/**
	 * 判断是否成功： 依据 MvcResult.SUCCESS_CODE == this.code
	 *
	 * @return code =200,true;否则 false.
	 */
	@JsonIgnore
	public boolean success() {
		return MvcResult.SUCCESS_CODE == this.code;
	}

	/**
	 * 判断是否成功： 依据 MvcResult.SUCCESS_CODE != this.code
	 *
	 * @return code !=200,true;否则 false.
	 */
	@JsonIgnore
	public boolean error() {
		return !success();
	}

}
