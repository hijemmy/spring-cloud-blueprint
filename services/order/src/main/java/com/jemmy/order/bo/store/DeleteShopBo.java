package com.jemmy.order.bo.store;

import lombok.Data;

import java.util.Date;

/**
 * @author Jemmy
 */
@Data
public class DeleteShopBo {
    private Long uid;
    private Date update_time;
    private Date delete_time;
}
