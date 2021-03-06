package com.jemmy.apis.edriven;

import com.jemmy.apis.edriven.model.dto.TpcMqMessageDto;
import com.jemmy.common.security.feign.OAuth2FeignAutoConfiguration;
import com.jemmy.common.util.wrapper.MvcResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import static com.jemmy.common.base.constant.GlobalConstant.MICRO_SERVICE_NAME_EDRIVEN;


/**
 * The interface Tpc mq message feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = MICRO_SERVICE_NAME_EDRIVEN, configuration = OAuth2FeignAutoConfiguration.class,  fallback = ReliableMqMessageFallbackFactory.class)
public interface TpcMqMessageFeignApi {

	/**
	 * 预存储消息.
	 *
	 * @param mqMessageDto the mq message dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/tpc/saveMessageWaitingConfirm")
    MvcResult saveMessageWaitingConfirm(@RequestBody TpcMqMessageDto mqMessageDto);

	/**
	 * 确认并发送消息.
	 *
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/tpc/confirmAndSendMessage")
    MvcResult confirmAndSendMessage(@RequestParam("messageKey") String messageKey);

	/**
	 * 存储并发送消息.
	 *
	 * @param mqMessageDto the mq message dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/tpc/saveAndSendMessage")
    MvcResult saveAndSendMessage(@RequestBody TpcMqMessageDto mqMessageDto);

	/**
	 * 直接发送消息.
	 *
	 * @param mqMessageDto the mq message dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/tpc/directSendMessage")
    MvcResult directSendMessage(@RequestBody TpcMqMessageDto mqMessageDto);

	/**
	 * 根据messageKey删除消息记录.
	 *
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/tpc/deleteMessageByMessageKey")
    MvcResult deleteMessageByMessageKey(@RequestParam("messageKey") String messageKey);

	/**
	 * Confirm receive message wrapper.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/tpc/confirmReceiveMessage")
    MvcResult confirmReceiveMessage(@RequestParam("cid") final String cid, @RequestParam("messageKey") final String messageKey);

	/**
	 * Save and confirm finish message wrapper.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/tpc/saveAndConfirmFinishMessage")
    MvcResult confirmConsumedMessage(@RequestParam("cid") final String cid, @RequestParam("messageKey") final String messageKey);
}
@Component
@Slf4j
class ReliableMqMessageFallbackFactory implements TpcMqMessageFeignApi{


	@Override
	public MvcResult saveMessageWaitingConfirm(@RequestBody TpcMqMessageDto mqMessageDto) {
		log.error("saveMessageWaitingConfirm - 服务降级. mqMessageDto={}", mqMessageDto);
		return null;
	}

	@Override
	public MvcResult confirmAndSendMessage(@RequestParam("messageKey") String messageKey) {
		return null;
	}

	@Override
	public MvcResult saveAndSendMessage(@RequestBody TpcMqMessageDto mqMessageDto) {
		return null;
	}

	@Override
	public MvcResult directSendMessage(@RequestBody TpcMqMessageDto mqMessageDto) {
		return null;
	}

	@Override
	public MvcResult deleteMessageByMessageKey(@RequestParam("messageKey") String messageKey) {
		return null;
	}

	@Override
	public MvcResult confirmReceiveMessage(@RequestParam("cid") String cid, @RequestParam("messageKey") String messageKey) {
		return null;
	}

	@Override
	public MvcResult confirmConsumedMessage(@RequestParam("cid") String cid, @RequestParam("messageKey") String messageKey) {
		return null;
	}
}