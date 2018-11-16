/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacGroupUser.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.services.user.model.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The class Uac group user.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@Table(name = "pc_uac_group_user")
@Alias(value = "uacGroupUser")
public class UacGroupUser implements Serializable {
	private static final long serialVersionUID = 5167709959453148144L;

	/**
	 * 角色ID
	 */
	@Column(name = "group_id")
	private Long groupId;

	/**
	 * 菜单ID
	 */
	@Column(name = "user_id")
	private Long userId;
}