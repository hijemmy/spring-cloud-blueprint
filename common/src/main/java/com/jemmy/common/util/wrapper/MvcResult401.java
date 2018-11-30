package com.jemmy.common.util.wrapper;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import static com.jemmy.common.base.enums.ErrorCodeEnum.GL99990401;

/**
 * @author Jemmy
 */
@ApiModel("MvcResult401")
@Data
public class MvcResult401 extends MvcResult{
    /**
     * 编号.
     */
    @ApiModelProperty(example = "99990401")
    private int code;

    /**
     * 信息.
     */
    @ApiModelProperty(example = "未授权")
    private String message;
    public MvcResult401() {
        setCode(GL99990401.code());
        setMessage(GL99990401.msg());
    }
}
