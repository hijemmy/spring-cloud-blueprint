package com.jemmy.services.barcode.model.vo.barcode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "数据详情",description = "数据详情")
public class TestVo {
    @ApiModelProperty(required = true,value = "姓名")
    private String name;
    @ApiModelProperty(required = true,value = "年龄")
    private Integer age;
}
