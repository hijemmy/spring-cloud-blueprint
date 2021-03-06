/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：HandleWaitingConfirmMessageJob.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.edriven.job.dataflow;

import com.google.common.collect.Lists;
import com.jemmy.apis.edriven.model.enums.JobTaskStatusEnum;
import com.jemmy.common.util.DateUtil;
import com.jemmy.elastic.lite.JobParameter;
import com.jemmy.elastic.lite.annotation.ElasticJobConfig;
import com.jemmy.elastic.lite.job.AbstractBaseDataflowJob;
import com.jemmy.services.edriven.model.dto.MessageTaskQueryDto;
import com.jemmy.services.edriven.model.enums.MqSendStatusEnum;
import com.jemmy.services.edriven.service.TpcMqMessageService;
import com.jemmy.services.edriven.service.UacRpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static com.jemmy.common.base.constant.GlobalConstant.ROOT_PREFIX;

/**
 * 处理可靠消息表中待生产者确认的消息数据,目的在于确保可靠消息服务与上游(生产者)之间的一致性
 *与生产比对待确认消息列表,可靠消息中多出的部分为不需要投递的消息,将其从可靠消息表中删除;
 * 其余的消息予以确认并重新投递
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Component
@ElasticJobConfig(cron = "0 0/10 * * * ?", jobParameter = "fetchNum=1000")
public class HandleWaitingConfirmMessageJob extends AbstractBaseDataflowJob<String> {
	@Resource
	private TpcMqMessageService tpcMqMessageService;
	@Resource
	private UacRpcService uacRpcService;
	@Value("${"+ROOT_PREFIX+".message.handleTimeout}")
	private int timeOutMinute;
	private static final String PID_UAC = "PID_UAC";

	/**
	 * Fetch job data list.
	 *
	 * @param jobParameter the job parameter
	 *
	 * @return the list
	 */
	@Override
	protected List<String> fetchJobData(JobParameter jobParameter) {
		MessageTaskQueryDto query = new MessageTaskQueryDto();
		query.setCreateTimeBefore(DateUtil.getBeforeTime(timeOutMinute));
		query.setMessageStatus(MqSendStatusEnum.WAIT_SEND.sendStatus());
		query.setFetchNum(jobParameter.getFetchNum());
		query.setShardingItem(jobParameter.getShardingItem());
		query.setShardingTotalCount(jobParameter.getShardingTotalCount());
		query.setTaskStatus(JobTaskStatusEnum.TASK_CREATE.status());
		query.setProducerGroup(PID_UAC);
		return tpcMqMessageService.queryWaitingConfirmMessageKeyList(query);
	}

	/**
	 * Process job data.
	 *
	 * @param messageKeyList the message key list
	 */
	@Override
	protected void processJobData(List<String> messageKeyList) {
		if (messageKeyList == null) {
			return;
		}
		List<String> resendMessageList = uacRpcService.queryWaitingConfirmMessageKeyList(messageKeyList);
		if (resendMessageList == null) {
			resendMessageList = Lists.newArrayList();
		}
		messageKeyList.removeAll(resendMessageList);
		tpcMqMessageService.handleWaitingConfirmMessage(messageKeyList, resendMessageList);
	}
}
