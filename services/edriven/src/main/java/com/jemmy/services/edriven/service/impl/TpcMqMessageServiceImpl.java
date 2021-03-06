/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqMessageServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.edriven.service.impl;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.jemmy.apis.edriven.exceptions.TpcBizException;
import com.jemmy.apis.edriven.model.dto.TpcMqMessageDto;
import com.jemmy.common.base.dto.MessageQueryDto;
import com.jemmy.common.base.dto.MqMessageVo;
import com.jemmy.common.base.enums.ErrorCodeEnum;
import com.jemmy.common.core.support.BaseService;
import com.jemmy.common.util.PublicUtil;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import com.jemmy.common.zk.generator.UniqueIdGenerator;
import com.jemmy.services.edriven.mapper.TpcMqConfirmMapper;
import com.jemmy.services.edriven.mapper.TpcMqMessageMapper;
import com.jemmy.services.edriven.model.domain.TpcMqConfirm;
import com.jemmy.services.edriven.model.domain.TpcMqMessage;
import com.jemmy.services.edriven.model.dto.MessageTaskQueryDto;
import com.jemmy.services.edriven.model.enums.MqSendStatusEnum;
import com.jemmy.services.edriven.model.enums.PIDEnum;
import com.jemmy.services.edriven.model.vo.TpcMessageVo;
import com.jemmy.services.edriven.mq.RocketMqProducer;
import com.jemmy.services.edriven.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * The class Tpc mq message service.
 *
 * @author paascloud.net @gmail.com
 */
@Service
@Slf4j
@Transactional(rollbackFor = Throwable.class)
public class TpcMqMessageServiceImpl extends BaseService<TpcMqMessage,TpcMqMessageMapper> implements TpcMqMessageService {
	
	@Resource
	private TpcMqConfirmMapper tpcMqConfirmMapper;
	@Resource
	private TpcMqConsumerService tpcMqConsumerService;
	@Resource
	private UacRpcService uacRpcService;
	@Resource
	private MdcRpcService mdcRpcService;
	@Resource
	private OpcRpcService opcRpcService;

	@Override
	public void saveMessageWaitingConfirm(TpcMqMessageDto messageDto) {

		if (StringUtils.isEmpty(messageDto.getMessageTopic())) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050001);
		}

		Date now = new Date();
		TpcMqMessage message = new ModelMapper().map(messageDto, TpcMqMessage.class);
		message.setMessageStatus(MqSendStatusEnum.WAIT_SEND.sendStatus());
		message.setUpdateTime(now);
		message.setCreatedTime(now);
		mapper.insertSelective(message);
	}

	@Override
	public void confirmAndSendMessage(String messageKey) {
		final TpcMqMessage message = mapper.getByMessageKey(messageKey);
		if (message == null) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050002);
		}

		TpcMqMessage update = new TpcMqMessage();
		update.setMessageStatus(MqSendStatusEnum.SENDING.sendStatus());
		update.setId(message.getId());
		update.setUpdateTime(new Date());
		mapper.updateByPrimaryKeySelective(update);
		// 创建消费待确认列表
		this.createMqConfirmListByTopic(message.getMessageTopic(), message.getId(), message.getMessageKey());
		// 直接发送消息
		this.directSendMessage(message.getMessageBody(), message.getMessageTopic(), message.getMessageTag(), message.getMessageKey(), message.getProducerGroup(), message.getDelayLevel());
	}

	@Override
	public void saveAndSendMessage(TpcMqMessageDto tpcMqMessageDto) {
		if (StringUtils.isEmpty(tpcMqMessageDto.getMessageTopic())) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050001);
		}

		Date now = new Date();
		TpcMqMessage message = new ModelMapper().map(tpcMqMessageDto, TpcMqMessage.class);
		message.setMessageStatus(MqSendStatusEnum.SENDING.sendStatus());
		message.setId(generateId());
		message.setUpdateTime(now);
		message.setCreatedTime(now);

		mapper.insertSelective(message);
		// 创建消费待确认列表
		this.createMqConfirmListByTopic(message.getMessageTopic(), message.getId(), message.getMessageKey());
		this.directSendMessage(tpcMqMessageDto.getMessageBody(), tpcMqMessageDto.getMessageTopic(), tpcMqMessageDto.getMessageTag(), tpcMqMessageDto.getMessageKey(), tpcMqMessageDto.getProducerGroup(), tpcMqMessageDto.getDelayLevel());
	}

	@Override
	public void directSendMessage(String body, String topic, String tag, String key, String pid, Integer delayLevel) {
		RocketMqProducer.sendSimpleMessage(body, topic, tag, key, pid, delayLevel);
	}

	@Override
	public void resendMessageByMessageId(Long messageId) {
		final TpcMqMessage message = mapper.selectByPrimaryKey(messageId);
		if (message == null) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050006);
		}
		this.resendMessage(message);
	}

	@Override
	public void resendMessageByMessageKey(String messageKey) {
		final TpcMqMessage task = mapper.getByMessageKey(messageKey);
		this.resendMessage(task);
	}

	@Override
	public void setMessageToAlreadyDead(Long messageId) {
		final TpcMqMessage task = mapper.selectByPrimaryKey(messageId);
		if (task == null) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050006);
		}

		mapper.updateAlreadyDeadByMessageId(messageId);
	}

	@Override
	public void deleteMessageByMessageKey(String messageKey) {

		int result = mapper.deleteMessageByMessageKey(messageKey);
		if (result < 1) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050003, messageKey);
		}
	}

	@Override
	public void resendAllDeadMessageByTopicName(String topicName, int batchSize) {
		// 1.查询该topic下所有死亡的消息
		// 2.分页
	}

	@Override
	public List<TpcMqMessage> listMessageForWaitingProcess(MessageTaskQueryDto query) {
		return mapper.listMessageForWaitingProcess(query);
	}

	@Override
	public void confirmReceiveMessage(final String cid, final String messageKey) {
		// 1. 校验cid
		// 2. 校验messageKey
		// 3. 校验cid 和 messageKey
		Long confirmId = tpcMqConfirmMapper.getIdMqConfirm(cid, messageKey);
		// 3. 更新消费信息的状态
		tpcMqConfirmMapper.confirmReceiveMessage(confirmId);
	}

	@Override
	public void confirmConsumedMessage(final String cid, final String messageKey) {
		Long confirmId = tpcMqConfirmMapper.getIdMqConfirm(cid, messageKey);
		tpcMqConfirmMapper.confirmConsumedMessage(confirmId);
	}

	@Override
	public int updateMqConfirmStatus(final String cid, final String messageKey) {
		return 0;
	}

	@Override
	public void createMqConfirmListByTopic(final String topic, final Long messageId, final String messageKey) {
		List<TpcMqConfirm> list = Lists.newArrayList();
		TpcMqConfirm tpcMqConfirm;
		List<String> consumerGroupList = tpcMqConsumerService.listConsumerGroupByTopic(topic);
		if (PublicUtil.isEmpty(consumerGroupList)) {
			throw new TpcBizException(ErrorCodeEnum.TPC100500010, topic);
		}
		for (final String cid : consumerGroupList) {
			tpcMqConfirm = new TpcMqConfirm(UniqueIdGenerator.generateId(), messageId, messageKey, cid);
			list.add(tpcMqConfirm);
		}

		tpcMqConfirmMapper.batchCreateMqConfirm(list);
	}

	@Override
	public List<String> queryWaitingConfirmMessageKeyList(final MessageTaskQueryDto query) {
		return mapper.queryWaitingConfirmMessageKeyList(query);
	}

	@Override
	public void handleWaitingConfirmMessage(final List<String> deleteKeyList, final List<String> resendKeyList) {
		mapper.batchDeleteMessage(deleteKeyList);
		for (final String messageKey : resendKeyList) {
			this.confirmAndSendMessage(messageKey);
		}
	}

	@Override
	public int updateMqMessageTaskStatus(final TpcMqMessage message) {
		return mapper.updateMqMessageTaskStatus(message);
	}

	@Override
	public int updateMqMessageStatus(final TpcMqMessage update) {
		return mapper.updateByPrimaryKeySelective(update);
	}

	@Override
	public MvcResult queryRecordListWithPage(final MessageQueryDto messageQueryDto) {
		String producerGroup = messageQueryDto.getProducerGroup();
		String messageKey = messageQueryDto.getMessageKey();
		Preconditions.checkArgument(StringUtils.isNotEmpty(producerGroup) || StringUtils.isNotEmpty(messageKey), "messageKey 和 pid 必须选择一个");
		if (StringUtils.isEmpty(producerGroup)) {
			List<MqMessageVo> result = Lists.newArrayList();
			MvcResult<PageInfo<MqMessageVo>> uacMvcResult = uacRpcService.queryMessageListWithPage(messageQueryDto);
			MvcResult<PageInfo<MqMessageVo>> mdcMvcResult = mdcRpcService.queryMessageListWithPage(messageQueryDto);
			MvcResult<PageInfo<MqMessageVo>> opcMvcResult = opcRpcService.queryMessageListWithPage(messageQueryDto);

			if (uacMvcResult != null && uacMvcResult.getResult() != null) {
				List<MqMessageVo> list = uacMvcResult.getResult().getList();
				result.addAll(list);
			}
			if (mdcMvcResult != null && mdcMvcResult.getResult() != null) {
				List<MqMessageVo> list = mdcMvcResult.getResult().getList();
				result.addAll(list);
			}
			if (opcMvcResult != null && opcMvcResult.getResult() != null) {
				List<MqMessageVo> list = opcMvcResult.getResult().getList();
				result.addAll(list);
			}
			return MvcResultBuilder.ok(new PageInfo<>(result));
		}
		if (StringUtils.equals(PIDEnum.PID_UAC.name(), producerGroup)) {
			return uacRpcService.queryMessageListWithPage(messageQueryDto);
		} else if (StringUtils.equals(PIDEnum.PID_MDC.name(), producerGroup)) {
			return mdcRpcService.queryMessageListWithPage(messageQueryDto);
		} else if (StringUtils.equals(PIDEnum.PID_OPC.name(), producerGroup)) {
			return opcRpcService.queryMessageListWithPage(messageQueryDto);
		} else {
			log.error("pid没有维护 pid={}", producerGroup);
		}
		return null;
	}

	@Override
	public List<TpcMessageVo> listReliableMessageVo(final MessageQueryDto messageQueryDto) {
		return mapper.listReliableMessageVoWithPage(messageQueryDto);
	}

	@Override
	public List<TpcMessageVo> listReliableMessageVo(final List<Long> messageIdList) {
		return mapper.listReliableMessageVo(messageIdList);
	}

	private void resendMessage(TpcMqMessage message) {
		if (message == null) {
			throw new TpcBizException(ErrorCodeEnum.TPC10050002);
		}
		mapper.addTaskExeCountById(message.getId());
		//TODO 记录重发日志 1.系统自动重发 2.人工重发
		this.directSendMessage(message.getMessageBody(), message.getMessageTopic(), message.getMessageTag(), message.getMessageKey(), message.getProducerGroup(), message.getDelayLevel());
	}
}
