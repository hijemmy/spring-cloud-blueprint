package com.jemmy.order.service;

import com.jemmy.common.vo.order.order.PagingVo;

public interface OrderService {

    PagingVo paging(Long uid, Byte status, Integer page, Integer pageSize);
}
