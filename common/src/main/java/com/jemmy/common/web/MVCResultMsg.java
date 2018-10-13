package com.jemmy.common.web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * Created by Jemmy on 2017-08-07.
 */
public class MVCResultMsg<MSG> {
    @JsonView(RootJsonView.class)
    ResultCode code=ResultCode.SUCCESS;

    @JsonView(RootJsonView.class)
    String message="执行成功";
    @JsonView(RootJsonView.class)
    MSG data;

    public MVCResultMsg() {
    }

    public ResultCode getCode() {
        return code;
    }

    public void setCode(ResultCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MSG getData() {
        return data;
    }

    public void setData(MSG data) {
        this.data = data;
    }

    @JsonIgnore
    public boolean isInValidate(){
        return ResultCode.FAIL.equals(code)||null==data;
    }

    @Override
    public String toString() {
        return "MVCResultMsg{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
