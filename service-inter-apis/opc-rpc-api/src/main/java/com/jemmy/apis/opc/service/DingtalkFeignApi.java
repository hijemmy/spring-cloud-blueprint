/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：DingtalkFeignApi.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.apis.opc.service;


import com.jemmy.apis.opc.model.dto.robot.ChatRobotMsgDto;
import com.jemmy.apis.opc.service.hystrix.DingtalkFeignApiHystrix;
import com.jemmy.common.security.feign.OAuth2FeignAutoConfiguration;
import com.jemmy.common.util.annotation.NoNeedAccessAuthentication;
import com.jemmy.common.util.wrapper.MvcResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.jemmy.common.base.constant.GlobalConstant.MICRO_SERVICE_NAME_OPC;

/**
 * The interface Dingtalk feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = MICRO_SERVICE_NAME_OPC, configuration = OAuth2FeignAutoConfiguration.class, fallback = DingtalkFeignApiHystrix.class)
public interface DingtalkFeignApi {

	/**
	 * 钉钉机器人推送消息.
	 *
	 * @param uacUserReqDto the uac user req dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/opc/dingtalk/sendChatRobotMsg")
	@NoNeedAccessAuthentication
    MvcResult<Boolean> sendChatRobotMsg(@RequestBody ChatRobotMsgDto uacUserReqDto);
}
