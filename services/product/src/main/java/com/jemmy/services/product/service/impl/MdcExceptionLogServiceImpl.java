/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcExceptionLogServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.product.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jemmy.apis.opc.model.dto.robot.ChatRobotMsgDto;
import com.jemmy.apis.opc.model.factory.ChatRobotMsgFactory;
import com.jemmy.apis.product.model.dto.GlobalExceptionLogDto;
import com.jemmy.common.base.constant.GlobalConstant;
import com.jemmy.common.core.support.BaseService;
import com.jemmy.services.product.mapper.MdcExceptionLogMapper;
import com.jemmy.services.product.model.domain.MdcExceptionLog;
import com.jemmy.services.product.model.dto.MdcExceptionQueryDto;
import com.jemmy.services.product.service.MdcExceptionLogService;
import com.jemmy.services.product.service.OpcRpcService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.jemmy.common.base.constant.GlobalConstant.ROOT_PREFIX;

/**
 * The class Mdc exception log service.
 *
 * @author paascloud.net @gmail.com
 */
@Service
public class MdcExceptionLogServiceImpl extends BaseService<MdcExceptionLog,MdcExceptionLogMapper> implements MdcExceptionLogService {
	@Resource
	private TaskExecutor taskExecutor;
	@Resource
	private OpcRpcService opcRpcService;
	@Value("${"+ROOT_PREFIX+".dingTalk.webhookToken.sendException}")
	private String webhookToken;

	@Override
	public void saveAndSendExceptionLog(final GlobalExceptionLogDto exceptionLogDto) {
		MdcExceptionLog exceptionLog = new ModelMapper().map(exceptionLogDto, MdcExceptionLog.class);

		exceptionLog.setId(generateId());
		exceptionLog.setCreateTime(new Date());
		mapper.insertSelective(exceptionLog);

		taskExecutor.execute(() -> {
			if (judgeIsSend(exceptionLogDto.getProfile())) {
				String text = exceptionLog.getApplicationName() + "出现异常. 环境：" + exceptionLogDto.getProfile() + "，操作人：" + exceptionLogDto.getCreator() + ".异常类型：" + exceptionLogDto.getExceptionSimpleName();
				ChatRobotMsgDto chatRobotMsgDto = ChatRobotMsgFactory.createChatRobotTextMsg(webhookToken, text, false, null);
				opcRpcService.sendChatRobotMsg(chatRobotMsgDto);
			}
		});

	}

	@Override
	public PageInfo queryExceptionListWithPage(final MdcExceptionQueryDto mdcExceptionQueryDto) {
		PageHelper.startPage(mdcExceptionQueryDto.getPageNum(), mdcExceptionQueryDto.getPageSize());
		List<MdcExceptionLog> actionList = mapper.queryExceptionListWithPage(mdcExceptionQueryDto);
		return new PageInfo<>(actionList);
	}

	private boolean judgeIsSend(String profile) {
		Calendar calendar = Calendar.getInstance();
		int time = calendar.get(Calendar.HOUR_OF_DAY);
		return GlobalConstant.PRO_PROFILE.equals(profile) || time >= 10 && time <= 18;
	}
}