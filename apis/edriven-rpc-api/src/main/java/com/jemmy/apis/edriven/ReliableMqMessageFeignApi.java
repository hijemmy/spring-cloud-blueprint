package com.jemmy.apis.edriven;

import com.jemmy.apis.edriven.io.RMqMessageIo;
import com.jemmy.common.web.MVCResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * The interface Tpc mq message feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "edriven", primary = false, fallback = ReliableMqMessageFallbackFactory.class)
public interface ReliableMqMessageFeignApi {

	/**
	 * 预存储消息.
	 *
	 * @param mqMessageDto the mq message dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/rmq/saveMessageWaitingConfirm")
	MVCResultMsg saveMessageWaitingConfirm(@RequestBody RMqMessageIo mqMessageDto);

	/**
	 * 确认并发送消息.
	 *
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/rmq/confirmAndSendMessage")
	MVCResultMsg confirmAndSendMessage(@RequestParam("messageKey") String messageKey);

	/**
	 * 存储并发送消息.
	 *
	 * @param mqMessageDto the mq message dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/rmq/saveAndSendMessage")
	MVCResultMsg saveAndSendMessage(@RequestBody RMqMessageIo mqMessageDto);

	/**
	 * 直接发送消息.
	 *
	 * @param mqMessageDto the mq message dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/rmq/directSendMessage")
	MVCResultMsg directSendMessage(@RequestBody RMqMessageIo mqMessageDto);

	/**
	 * 根据messageKey删除消息记录.
	 *
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/rmq/deleteMessageByMessageKey")
	MVCResultMsg deleteMessageByMessageKey(@RequestParam("messageKey") String messageKey);

	/**
	 * Confirm receive message wrapper.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/rmq/confirmReceiveMessage")
	MVCResultMsg confirmReceiveMessage(@RequestParam("cid") final String cid, @RequestParam("messageKey") final String messageKey);

	/**
	 * Save and confirm finish message wrapper.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/rmq/saveAndConfirmFinishMessage")
	MVCResultMsg confirmConsumedMessage(@RequestParam("cid") final String cid, @RequestParam("messageKey") final String messageKey);
}
@Component
@Slf4j
class ReliableMqMessageFallbackFactory implements ReliableMqMessageFeignApi{

	@Override
	public MVCResultMsg saveMessageWaitingConfirm(RMqMessageIo mqMessageDto) {
		log.error("saveMessageWaitingConfirm - 服务降级. mqMessageDto={}", mqMessageDto);
		return null;
	}

	@Override
	public MVCResultMsg confirmAndSendMessage(String messageKey) {
		return null;
	}

	@Override
	public MVCResultMsg saveAndSendMessage(RMqMessageIo mqMessageDto) {
		return null;
	}

	@Override
	public MVCResultMsg directSendMessage(RMqMessageIo mqMessageDto) {
		return null;
	}

	@Override
	public MVCResultMsg deleteMessageByMessageKey(String messageKey) {
		return null;
	}

	@Override
	public MVCResultMsg confirmReceiveMessage(String cid, String messageKey) {
		return null;
	}

	@Override
	public MVCResultMsg confirmConsumedMessage(String cid, String messageKey) {
		return null;
	}
}