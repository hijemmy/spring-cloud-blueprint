/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacRoleAction.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.user.model.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The class Uac role action.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@Table(name = "pc_uac_role_action")
@Alias(value = "uacRoleAction")
public class UacRoleAction implements Serializable {
	private static final long serialVersionUID = -4240611881810188284L;

	@Id
	@Column(name = "role_id")
	private Long roleId;

	@Id
	@Column(name = "action_id")
	private Long actionId;
}