package com.jemmy.order.vo.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Jemmy
 */
@Data
public class PagingItem {
    private Long id;
    private BigDecimal total;
    private Byte state;
    private Date createTime;
}
