/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacActionCommonController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.user.web.admin;


import com.jemmy.common.core.support.BaseController;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.services.user.model.domain.UacAction;
import com.jemmy.services.user.model.dto.action.UacActionCheckCodeDto;
import com.jemmy.services.user.model.dto.action.UacActionCheckUrlDto;
import com.jemmy.services.user.service.UacActionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * The class Uac action common controller.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@RequestMapping(value = "/action", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacActionCommonController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacActionCommonController extends BaseController {


	@Resource
	private UacActionService uacActionService;

	/**
	 * 检测权限编码是否已存在
	 *
	 * @param uacActionCheckCodeDto the uac action check code dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkActionCode")
	@ApiOperation(httpMethod = "POST", value = "检测权限编码是否已存在")
	public MvcResult<Boolean> checkActionCode(@ApiParam(name = "uacActionCheckCodeDto", value = "id与url") @RequestBody UacActionCheckCodeDto uacActionCheckCodeDto) {
		logger.info("校验权限编码唯一性 uacActionCheckCodeDto={}", uacActionCheckCodeDto);

		Long id = uacActionCheckCodeDto.getActionId();
		String actionCode = uacActionCheckCodeDto.getActionCode();

		Example example = new Example(UacAction.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("actionCode", actionCode);

		int result = uacActionService.selectCountByExample(example);
		return MvcResultBuilder.ok(result < 1);
	}

	/**
	 * 检测权限URL唯一性
	 *
	 * @param uacActionCheckUrlDto the uac action check url dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkUrl")
	@ApiOperation(httpMethod = "POST", value = "检测权限URL唯一性")
	public MvcResult<Boolean> checkActionUrl(@ApiParam(name = "uacActionCheckUrlDto", value = "id与url") @RequestBody UacActionCheckUrlDto uacActionCheckUrlDto) {
		logger.info("检测权限URL唯一性 uacActionCheckUrlDto={}", uacActionCheckUrlDto);

		Long id = uacActionCheckUrlDto.getActionId();
		String url = uacActionCheckUrlDto.getUrl();

		Example example = new Example(UacAction.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("url", url);

		int result = uacActionService.selectCountByExample(example);
		return MvcResultBuilder.ok(result < 1);
	}
}
