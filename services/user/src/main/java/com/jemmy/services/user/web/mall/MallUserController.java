/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MallUserController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.user.web.mall;

import com.jemmy.apis.user.user.UserInfoDto;
import com.jemmy.common.base.dto.LoginAuthDto;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.services.user.model.domain.UacUser;
import com.jemmy.services.user.service.UacUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * The class Mall user controller.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MallUserController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MallUserController extends BaseController {
	@Resource
	private UacUserService uacUserService;

	/**
	 * 更新用户信息.
	 *
	 * @param userInfoDto the user info dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/updateInformation")
	@ApiOperation(httpMethod = "POST", value = "更新用户信息")
	public MvcResult<UserInfoDto> updateInformation(@RequestBody UserInfoDto userInfoDto) {
		logger.info("updateInformation - 更新用户基本信息 userInfoDto={}", userInfoDto);
		UacUser uacUser = new ModelMapper().map(userInfoDto, UacUser.class);
		uacUserService.updateUser(uacUser);
		return MvcResultBuilder.ok();
	}

	/**
	 * 获取用户信息.
	 *
	 * @return the information
	 */
	@PostMapping(value = "/getInformation")
	@ApiOperation(httpMethod = "POST", value = "获取用户信息")
	public MvcResult<UserInfoDto> getInformation() {
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		Long userId = loginAuthDto.getUserId();
		logger.info("queryUserInfo - 查询用户基本信息 userId={}", userId);
		UacUser uacUser = uacUserService.queryByUserId(userId);
		if (uacUser == null) {
			return MvcResultBuilder.error("找不到当前用户");
		}
		UserInfoDto userInfoDto = new UserInfoDto();
		BeanUtils.copyProperties(uacUser, userInfoDto);

		return MvcResultBuilder.ok(userInfoDto);
	}
}