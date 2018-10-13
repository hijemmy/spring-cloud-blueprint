package com.jemmy.order.service;

import com.github.pagehelper.Page;
import com.jemmy.common.vo.order.order.PagingItem;
import com.jemmy.common.vo.order.order.PagingVo;

import java.util.Date;

public interface OrderService {

    PagingVo paging(Long uid, Byte status, Integer page, Integer pageSize);
}
