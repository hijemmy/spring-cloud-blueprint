/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacMqMessageApiHystrix.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.apis.user.service.hystrix;


import com.github.pagehelper.PageInfo;
import com.jemmy.apis.user.service.UacMqMessageFeignApi;
import com.jemmy.common.base.dto.MessageQueryDto;
import com.jemmy.common.base.dto.MqMessageVo;
import com.jemmy.common.util.wrapper.MvcResult;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * The class Uac mq message api hystrix.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class UacMqMessageApiHystrix implements UacMqMessageFeignApi {

	@Override
	public MvcResult<List<String>> queryMessageKeyList(final List<String> messageKeyList) {
		return null;
	}

	@Override
	public MvcResult<PageInfo<MqMessageVo>> queryMessageListWithPage(final MessageQueryDto messageQueryDto) {
		return null;
	}

}
