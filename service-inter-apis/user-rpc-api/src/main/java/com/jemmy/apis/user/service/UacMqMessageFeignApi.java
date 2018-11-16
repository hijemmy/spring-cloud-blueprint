/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacMqMessageFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.apis.user.service;


import com.github.pagehelper.PageInfo;
import com.jemmy.apis.user.service.hystrix.UacMqMessageApiHystrix;
import com.jemmy.common.base.dto.MessageQueryDto;
import com.jemmy.common.base.dto.MqMessageVo;
import com.jemmy.common.security.feign.OAuth2FeignAutoConfiguration;
import com.jemmy.common.util.wrapper.MvcResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.jemmy.common.base.constant.GlobalConstant.MICRO_SERVICE_NAME_USER;

/**
 * The interface Uac user token feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = MICRO_SERVICE_NAME_USER, configuration = OAuth2FeignAutoConfiguration.class, fallback = UacMqMessageApiHystrix.class)
public interface UacMqMessageFeignApi {


	/**
	 * Query waiting confirm message list wrapper.
	 *
	 * @param messageKeyList the message key list
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/uac/message/queryMessageKeyList")
    MvcResult<List<String>> queryMessageKeyList(@RequestParam("messageKeyList") List<String> messageKeyList);

	/**
	 * Query message list with page wrapper.
	 *
	 * @param messageQueryDto the message query dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/uac/message/queryMessageListWithPage")
    MvcResult<PageInfo<MqMessageVo>> queryMessageListWithPage(@RequestBody MessageQueryDto messageQueryDto);
}
