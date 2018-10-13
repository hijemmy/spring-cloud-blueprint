package com.jemmy.common.web;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by Jemmy on 2017-08-07.
 */
public enum ResultCode {

    FAIL("失败"),SUCCESS("成功");

    //结果描述
    private final String desc;


    ResultCode( String desc) {
        this.desc = desc;
    }

    @JsonValue
    public int value(){
        return ordinal();
    }
}
