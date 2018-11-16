/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacRpcService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.edriven.service;

import com.github.pagehelper.PageInfo;
import com.jemmy.apis.edriven.exceptions.TpcBizException;
import com.jemmy.apis.user.service.UacMqMessageFeignApi;
import com.jemmy.apis.user.service.UacUserTokenFeignApi;
import com.jemmy.common.base.dto.MessageQueryDto;
import com.jemmy.common.base.dto.MqMessageVo;
import com.jemmy.common.base.enums.ErrorCodeEnum;
import com.jemmy.common.util.wrapper.MvcResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Uac rpc service.
 *
 * @author paascloud.net @gmail.com
 */
@Service
@Slf4j
public class UacRpcService {
	@Resource
	private UacUserTokenFeignApi uacUserTokenFeignApi;
	@Resource
	private UacMqMessageFeignApi uacMqMessageFeignApi;

	@Retryable(value = Exception.class, backoff = @Backoff(delay = 5000, multiplier = 2))
	public void batchUpdateTokenOffLine() {
		MvcResult<Integer> mvcResult = uacUserTokenFeignApi.updateTokenOffLine();
		if (mvcResult == null) {
			log.error("updateTokenOffLine 失败 result is null");
			return;
		}
		Integer result = mvcResult.getResult();
		if (result == null || result == 0) {
			log.error("updateTokenOffLine 失败");
		} else {
			log.info("updateTokenOffLine 成功");
		}
	}

	public List<String> queryWaitingConfirmMessageKeyList(List<String> messageKeyList) {
		MvcResult<List<String>> mvcResult = uacMqMessageFeignApi.queryMessageKeyList(messageKeyList);
		if (mvcResult == null) {
			log.error("queryWaitingConfirmMessageKeyList 失败 result is null");
			throw new TpcBizException(ErrorCodeEnum.GL99990002);
		}
		return mvcResult.getResult();
	}

	public MvcResult<PageInfo<MqMessageVo>> queryMessageListWithPage(MessageQueryDto messageQueryDto) {
		MvcResult<PageInfo<MqMessageVo>> mvcResult = uacMqMessageFeignApi.queryMessageListWithPage(messageQueryDto);
		if (mvcResult == null) {
			log.error("查询消息记录 失败 result is null");
			throw new TpcBizException(ErrorCodeEnum.GL99990002);
		}
		return mvcResult;
	}

}