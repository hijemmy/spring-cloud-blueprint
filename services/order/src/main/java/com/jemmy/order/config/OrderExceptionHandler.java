package com.jemmy.order.config;


import com.jemmy.common.web.MVCResultMsg;
import com.jemmy.common.web.ResultCode;
import com.jemmy.order.OrderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 *
 * @author Jemmy
 * @date 2017/3/27
 */
@RestControllerAdvice
@SuppressWarnings("unused")
@Slf4j
public class OrderExceptionHandler {



    @ExceptionHandler({OrderException.class})
    @ResponseStatus(HttpStatus.OK)
    public MVCResultMsg<String> orderException(OrderException ex){
        log.error("{}",ex.getMessage());
        MVCResultMsg<String> result=new MVCResultMsg<>();
        result.setCode(ResultCode.FAIL);
        result.setMessage(ex.getMessage());
        return result;
    }


}
