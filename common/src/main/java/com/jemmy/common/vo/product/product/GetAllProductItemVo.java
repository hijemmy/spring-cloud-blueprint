package com.jemmy.common.vo.product.product;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by pc on 2018/10/13.
 */
@Data
public class GetAllProductItemVo {
    private Long id;

    private String name;
    private Date createTime;
    private BigDecimal price;
}
