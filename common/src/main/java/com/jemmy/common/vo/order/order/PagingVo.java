package com.jemmy.common.vo.order.order;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author Jemmy
 */
@Data
public class PagingVo {
    private long count;
    private List<PagingItem> items;
}
