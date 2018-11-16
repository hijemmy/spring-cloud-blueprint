/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacLogMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.user.web.admin;

import com.github.pagehelper.PageInfo;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.services.user.model.dto.log.UacLogMainDto;
import com.jemmy.services.user.service.UacLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 日志管理.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@RequestMapping(value = "/log", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacLogMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacLogMainController extends BaseController {
	@Resource
	private UacLogService uacLogService;

	/**
	 * 查询日志列表.
	 *
	 * @param uacLogQueryDtoPage the uac log query dto page
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询日志列表")
	public MvcResult queryLogListWithPage(@ApiParam(name = "uacLogQueryDtoPage", value = "日志查询条件") @RequestBody UacLogMainDto uacLogQueryDtoPage) {
		logger.info("查询日志处理列表 uacLogQueryDtoPage={}", uacLogQueryDtoPage);
		PageInfo pageInfo = uacLogService.queryLogListWithPage(uacLogQueryDtoPage);
		return MvcResultBuilder.ok(pageInfo);
	}
}
