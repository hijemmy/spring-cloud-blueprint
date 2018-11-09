package com.jemmy.order.service;


import com.jemmy.order.vo.order.PagingVo;

public interface OrderService {

    PagingVo paging(Long uid, Byte status, Integer page, Integer pageSize);
}
