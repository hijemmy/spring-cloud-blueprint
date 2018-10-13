package com.jemmy.common.vo.product.product;


import lombok.Data;

import java.util.List;

/**
 * Created by pc on 2018/10/13.
 */
@Data
public class GetAllProductVo {
    private Long count;
    private List<GetAllProductItemVo> items;
}
