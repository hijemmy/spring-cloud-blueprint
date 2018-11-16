/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcRpcService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.edriven.service;


import com.github.pagehelper.PageInfo;
import com.jemmy.apis.edriven.exceptions.TpcBizException;
import com.jemmy.apis.opc.model.dto.robot.ChatRobotMsgDto;
import com.jemmy.apis.opc.service.DingtalkFeignApi;
import com.jemmy.apis.opc.service.OpcMqMessageFeignApi;
import com.jemmy.apis.opc.service.OpcOssFeignApi;
import com.jemmy.common.base.dto.MessageQueryDto;
import com.jemmy.common.base.dto.MqMessageVo;
import com.jemmy.common.base.enums.ErrorCodeEnum;
import com.jemmy.common.util.wrapper.MvcResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * The class Opc rpc service.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Component
public class OpcRpcService {

	@Resource
	private DingtalkFeignApi dingtalkFeignApi;
	@Resource
	private OpcOssFeignApi opcOssFeignApi;
	@Resource
	private OpcMqMessageFeignApi opcMqMessageFeignApi;

	/**
	 * Send chat robot msg boolean.
	 *
	 * @param chatRobotMsgDto the chat robot msg dto
	 *
	 * @return the boolean
	 */
	public boolean sendChatRobotMsg(ChatRobotMsgDto chatRobotMsgDto) {
		MvcResult<Boolean> result = dingtalkFeignApi.sendChatRobotMsg(chatRobotMsgDto);
		return result.getResult();
	}

	/**
	 * Delete expire file.
	 */
	public void deleteExpireFile() {
		opcOssFeignApi.deleteExpireFile();
	}

	public MvcResult<PageInfo<MqMessageVo>> queryMessageListWithPage(final MessageQueryDto messageQueryDto) {
		MvcResult<PageInfo<MqMessageVo>> mvcResult = opcMqMessageFeignApi.queryMessageListWithPage(messageQueryDto);
		if (mvcResult == null) {
			log.error("查询消息记录 失败 result is null");
			throw new TpcBizException(ErrorCodeEnum.GL99990002);
		}
		return mvcResult;
	}
}