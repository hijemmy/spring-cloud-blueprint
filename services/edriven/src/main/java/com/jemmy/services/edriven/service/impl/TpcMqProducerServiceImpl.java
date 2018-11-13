/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqProducerServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.edriven.service.impl;

import com.jemmy.common.core.support.BaseService;
import com.jemmy.services.edriven.mapper.TpcMqProducerMapper;
import com.jemmy.services.edriven.model.domain.TpcMqProducer;
import com.jemmy.services.edriven.model.vo.TpcMqProducerVo;
import com.jemmy.services.edriven.model.vo.TpcMqPublishVo;
import com.jemmy.services.edriven.service.TpcMqProducerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Tpc mq producer service.
 *
 * @author paascloud.net @gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TpcMqProducerServiceImpl extends BaseService<TpcMqProducer,TpcMqProducerMapper> implements TpcMqProducerService {

	@Override
	public List<TpcMqProducerVo> listProducerVoWithPage(TpcMqProducer mdcMqProducer) {
		return mapper.listTpcMqProducerVoWithPage(mdcMqProducer);
	}

	@Override
	public List<TpcMqPublishVo> listPublishVoWithPage(TpcMqProducer mdcMqProducer) {
		return mapper.listTpcMqPublishVoWithPage(mdcMqProducer);
	}

	@Override
	public int deleteProducerById(Long producerId) {
		// 删除consumer
		mapper.deleteByPrimaryKey(producerId);
		// 删除发布关系
		return mapper.deletePublishByProducerId(producerId);
	}

	@Override
	public void updateOnLineStatusByPid(final String producerGroup) {
		logger.info("更新生产者pid={}状态为在线", producerGroup);
		this.updateStatus(producerGroup, 10);

	}

	@Override
	public void updateOffLineStatusByPid(final String producerGroup) {
		logger.info("更新生产者pid={}状态为离线", producerGroup);
		this.updateStatus(producerGroup, 20);
	}

	private void updateStatus(final String producerGroup, final int status) {
		TpcMqProducer tpcMqProducer = mapper.getByPid(producerGroup);
		if (tpcMqProducer.getStatus() != null && tpcMqProducer.getStatus() != status) {
			TpcMqProducer update = new TpcMqProducer();
			update.setStatus(status);
			update.setId(tpcMqProducer.getId());
			mapper.updateByPrimaryKeySelective(update);
		}
	}
}
