/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqProducerMapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.edriven.mapper;

import com.jemmy.common.core.mybatis.RootMapper;
import com.jemmy.services.edriven.model.domain.TpcMqProducer;
import com.jemmy.services.edriven.model.vo.TpcMqProducerVo;
import com.jemmy.services.edriven.model.vo.TpcMqPublishVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Tpc mq producer mapper.
 *
 * @author paascloud.net @gmail.com
 */
@Mapper
@Component
public interface TpcMqProducerMapper extends RootMapper<TpcMqProducer> {

	/**
	 * 查询生产者集合.
	 *
	 * @param tpcMqProducer the tpc mq producer
	 *
	 * @return the list
	 */
	List<TpcMqProducerVo> listTpcMqProducerVoWithPage(TpcMqProducer tpcMqProducer);

	/**
	 * 查询发布消息集合.
	 *
	 * @param tpcMqProducer the tpc mq producer
	 *
	 * @return the list
	 */
	List<TpcMqPublishVo> listTpcMqPublishVoWithPage(TpcMqProducer tpcMqProducer);

	/**
	 * Delete publish by producer id int.
	 *
	 * @param producerId the producer id
	 *
	 * @return the int
	 */
	int deletePublishByProducerId(@Param("producerId") Long producerId);

	/**
	 * Gets by pid.
	 *
	 * @param producerGroup the producer group
	 *
	 * @return the by pid
	 */
	TpcMqProducer getByPid(@Param("pid") String producerGroup);

}