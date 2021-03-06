/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcMqMessageFeignClient.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.opc.web.rpc;


import com.github.pagehelper.PageInfo;
import com.jemmy.apis.opc.service.OpcMqMessageFeignApi;
import com.jemmy.apis.rmq.service.MqMessageService;
import com.jemmy.common.base.dto.MessageQueryDto;
import com.jemmy.common.base.dto.MqMessageVo;
import com.jemmy.common.core.support.BaseController;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.common.util.wrapper.MvcResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Mq 消息.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@Api(value = "API - OpcMqMessageFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OpcMqMessageFeignClient extends BaseController implements OpcMqMessageFeignApi {
	@Resource
	private MqMessageService mqMessageService;

	@Override
	@ApiOperation(httpMethod = "POST", value = "查询含有的messageKey")
	public MvcResult<List<String>> queryMessageKeyList(@RequestParam("messageKeyList") List<String> messageKeyList) {
		logger.info("查询消息KEY. messageKeyList={}", messageKeyList);
		return MvcResultBuilder.ok(mqMessageService.queryMessageKeyList(messageKeyList));
	}

	@Override
	public MvcResult<PageInfo<MqMessageVo>> queryMessageListWithPage(@RequestBody MessageQueryDto messageQueryDto) {
		return mqMessageService.queryMessageListWithPage(messageQueryDto);
	}
}
