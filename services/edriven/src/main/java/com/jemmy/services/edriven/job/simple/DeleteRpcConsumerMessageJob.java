/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：DeleteRpcConsumerMessageJob.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.edriven.job.simple;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.jemmy.apis.edriven.model.dto.TpcMqMessageDto;
import com.jemmy.common.base.constant.AliyunMqTopicConstants;
import com.jemmy.common.base.dto.ShardingContextDto;
import com.jemmy.common.config.properties.ApplicationProperties;
import com.jemmy.common.zk.generator.UniqueIdGenerator;
import com.jemmy.elastic.lite.annotation.ElasticJobConfig;
import com.jemmy.services.edriven.service.TpcMqMessageService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 定时清理所有订阅者消费成功的消息数据.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@ElasticJobConfig(cron = "0 0 0 1/1 * ?")
public class DeleteRpcConsumerMessageJob implements SimpleJob {
	@Resource
	private ApplicationProperties paascloudProperties;
	@Resource
	private TpcMqMessageService tpcMqMessageService;

	/**
	 * Execute.
	 *
	 * @param shardingContext the sharding context
	 */
	@Override
	public void execute(final ShardingContext shardingContext) {
		ShardingContextDto shardingContextDto = new ShardingContextDto(shardingContext.getShardingTotalCount(), shardingContext.getShardingItem());
		final TpcMqMessageDto message = new TpcMqMessageDto();
		message.setMessageBody(JSON.toJSONString(shardingContextDto));
		message.setMessageTag(AliyunMqTopicConstants.MqTagEnum.DELETE_CONSUMER_MESSAGE.getTag());
		message.setMessageTopic(AliyunMqTopicConstants.MqTopicEnum.TPC_TOPIC.getTopic());
		message.setProducerGroup(paascloudProperties.getAliyun().getRocketMq().getProducerGroup());
		String refNo = Long.toString(UniqueIdGenerator.generateId());
		message.setRefNo(refNo);
		message.setMessageKey(refNo);
		tpcMqMessageService.saveAndSendMessage(message);
	}
}
