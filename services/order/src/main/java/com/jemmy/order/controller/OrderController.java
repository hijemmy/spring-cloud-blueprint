package com.jemmy.order.controller;

import com.jemmy.common.annotation.JSONRequestMapping;
import com.jemmy.common.web.MVCResultMsg;
import com.jemmy.common.web.ResultCode;
import com.jemmy.order.service.OrderService;
import com.jemmy.order.vo.order.PagingVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * @author Jemmy
 */
@RestController
@JSONRequestMapping("/order")
@Slf4j
@Validated
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单分页列表
     * @param principal
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/paging")
    public MVCResultMsg<PagingVo> paging(@AuthenticationPrincipal String principal, @Valid @Min(0)@RequestParam Byte state, @RequestParam(defaultValue = "1") @Valid @Min(1) Integer page, @Valid @Min(1)@RequestParam(defaultValue = "10") Integer size){
        log.debug("uid:{}",principal);
        Long uid=Long.valueOf(principal);
        MVCResultMsg<PagingVo> resultMsg=new MVCResultMsg<>();
        resultMsg.setCode(ResultCode.SUCCESS);
        resultMsg.setData(orderService.paging(uid,state,page,size));
        return resultMsg;
    }

}