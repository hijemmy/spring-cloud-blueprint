package com.jemmy.order.vo.order;

import lombok.Data;

import java.util.List;

/**
 * @author Jemmy
 */
@Data
public class PagingVo {
    private long count;
    private List<PagingItem> items;
}
