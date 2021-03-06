/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderMapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.order.mapper;

import com.jemmy.apis.omc.model.dto.OrderPageQuery;
import com.jemmy.apis.omc.model.vo.OrderDocVo;
import com.jemmy.common.core.mybatis.RootMapper;
import com.jemmy.services.order.model.domain.OmcOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Omc order mapper.
 *
 * @author paascloud.net @gmail.com
 */
@Mapper
@Component
public interface OmcOrderMapper extends RootMapper<OmcOrder> {
	/**
	 * Select by user id and order no omc order.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 *
	 * @return the omc order
	 */
	OmcOrder selectByUserIdAndOrderNo(@Param("userId") Long userId, @Param("orderNo") String orderNo);

	/**
	 * Select by order no omc order.
	 *
	 * @param orderNo the order no
	 *
	 * @return the omc order
	 */
	OmcOrder selectByOrderNo(String orderNo);

	/**
	 * Select by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<OmcOrder> selectByUserId(Long userId);

	/**
	 * Select all order list.
	 *
	 * @return the list
	 */
	List<OmcOrder> selectAllOrder();

	/**
	 * Query order list with page list.
	 *
	 * @param orderPageQuery the order page query
	 *
	 * @return the list
	 */
	List<OrderDocVo> queryOrderListWithPage(OrderPageQuery orderPageQuery);
}