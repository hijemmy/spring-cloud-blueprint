/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqConsumerServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.edriven.service.impl;


import com.jemmy.common.core.support.BaseService;
import com.jemmy.common.util.PublicUtil;
import com.jemmy.services.edriven.mapper.TpcMqConsumerMapper;
import com.jemmy.services.edriven.model.domain.TpcMqConsumer;
import com.jemmy.services.edriven.model.vo.TpcMqConsumerVo;
import com.jemmy.services.edriven.model.vo.TpcMqSubscribeVo;
import com.jemmy.services.edriven.service.TpcMqConsumerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Tpc mq consumer service.
 *
 * @author paascloud.net @gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TpcMqConsumerServiceImpl extends BaseService<TpcMqConsumer,TpcMqConsumerMapper> implements TpcMqConsumerService {
	

	@Override
	public List<TpcMqConsumerVo> listConsumerVoWithPage(TpcMqConsumer tpcMqConsumer) {
		return mapper.listTpcMqConsumerVoWithPage(tpcMqConsumer);
	}

	@Override
	public List<TpcMqSubscribeVo> listSubscribeVoWithPage(TpcMqConsumer tpcMqConsumer) {
		return mapper.listTpcMqSubscribeVoWithPage(tpcMqConsumer);
	}

	@Override
	public int deleteSubscribeTagByTagId(Long tagId) {
		return mapper.deleteSubscribeTagByTagId(tagId);
	}

	@Override
	public int deleteConsumerById(Long consumerId) {
		// 删除消费者
		mapper.deleteByPrimaryKey(consumerId);
		// 删除订阅关系
		List<Long> subscribeIdList = mapper.listSubscribeIdByConsumerId(consumerId);
		if (PublicUtil.isNotEmpty(subscribeIdList)) {
			mapper.deleteSubscribeByConsumerId(consumerId);
			// 删除订阅tag
			mapper.deleteSubscribeTagBySubscribeIdList(subscribeIdList);
		}
		return 1;
	}

	@Override
	public List<TpcMqSubscribeVo> listSubscribeVo(List<Long> subscribeIdList) {
		return mapper.listSubscribeVo(subscribeIdList);
	}

	@Override
	public List<String> listConsumerGroupByTopic(final String topic) {
		return mapper.listConsumerGroupByTopic(topic);
	}

	@Override
	public void updateOnLineStatusByCid(final String consumerGroup) {
		logger.info("更新消费者cid={}状态为在线", consumerGroup);
		this.updateStatus(consumerGroup, 10);

	}

	@Override
	public void updateOffLineStatusByCid(final String consumerGroup) {
		logger.info("更新消费者cid={}状态为离线", consumerGroup);
		this.updateStatus(consumerGroup, 20);
	}

	private void updateStatus(final String consumerGroup, final int status) {
		TpcMqConsumer tpcMqConsumer = mapper.getByCid(consumerGroup);
		if (tpcMqConsumer.getStatus() != null && tpcMqConsumer.getStatus() != status) {
			TpcMqConsumer update = new TpcMqConsumer();
			update.setStatus(status);
			update.setId(tpcMqConsumer.getId());
			mapper.updateByPrimaryKeySelective(update);
		}
	}
}
