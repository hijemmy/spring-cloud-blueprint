package com.jemmy.order.bo.store;

import lombok.Data;

import java.util.Date;

/**
 * @author Jemmy
 */
@Data
public class ModifyShopNameBo {
    private Long shopId;
    private String shopName;
    private Date update_time;
}
