/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcAddressServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.product.service.impl;

import com.google.common.collect.Lists;
import com.jemmy.common.core.support.BaseService;
import com.jemmy.common.util.RecursionTreeUtil;
import com.jemmy.common.util.TreeNode;
import com.jemmy.services.product.mapper.MdcAddressMapper;
import com.jemmy.services.product.model.domain.MdcAddress;
import com.jemmy.services.product.service.MdcAddressService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Mdc address service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
public class MdcAddressServiceImpl extends BaseService<MdcAddress,MdcAddressMapper> implements MdcAddressService {

	/**
	 * Find by pid list.
	 *
	 * @param pid the pid
	 *
	 * @return the list
	 */
	@Override
	public List<MdcAddress> listByPid(Long pid) {
		return mapper.selectAddressByPid(pid);
	}

	@Override
	@Cacheable(cacheNames = "mdc-cache", key = "#id")
	public MdcAddress getById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	@Cacheable(cacheNames = "mdc-cache", keyGenerator = "keyGenerator")
	public List<TreeNode> get4City() {
		List<MdcAddress> mdcAddresses = mapper.selectAll();
		List<TreeNode> treeNodeList = buildGroupTree(mdcAddresses);
		logger.info("treeNodeList={}", treeNodeList);
		return treeNodeList;
	}

	private List<TreeNode> buildGroupTree(List<MdcAddress> mdcAddressesList) {
		List<TreeNode> list = Lists.newArrayList();
		TreeNode node;
		for (MdcAddress group : mdcAddressesList) {
			node = new TreeNode();
			node.setId(group.getId());
			node.setPid(group.getPid());
			node.setNodeCode(group.getAdCode());
			node.setNodeName(group.getName());
			list.add(node);
		}

		return RecursionTreeUtil.getChildTreeNodes(list, 368100107951677440L);
	}
}
