package com.jemmy.services.barcode.model.dto.barcode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class TestDto {
    @ApiModelProperty(value = "姓名",required = true)
    private String name;
}
