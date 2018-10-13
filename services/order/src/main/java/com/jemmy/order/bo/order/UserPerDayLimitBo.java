package com.jemmy.order.bo.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Jemmy
 */
@Data
public class UserPerDayLimitBo {
    private BigDecimal sumWeight;
    private BigDecimal sumMoney;
}
