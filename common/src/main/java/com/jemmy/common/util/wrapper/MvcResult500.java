package com.jemmy.common.util.wrapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("MvcResult500")
public class MvcResult500 extends MvcResult{
    /**
     * 编号.
     */
    @ApiModelProperty(example = "500")
    private int code;

    /**
     * 信息.
     */
    @ApiModelProperty(example = "未知异常")
    private String message;
    public MvcResult500() {
        super(MvcResult.ERROR_CODE,MvcResult.ERROR_MESSAGE);
    }
}
