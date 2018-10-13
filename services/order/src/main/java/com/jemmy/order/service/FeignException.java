package com.jemmy.order.service;

import com.netflix.client.ClientException;

public class FeignException extends Exception{

    public FeignException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        String msg;
        Throwable cause=this.getCause().getCause();
        if(cause instanceof ClientException)
            msg="内部服务调用错误";
        else
            msg="请求过于频繁";
        return msg;
    }
}
