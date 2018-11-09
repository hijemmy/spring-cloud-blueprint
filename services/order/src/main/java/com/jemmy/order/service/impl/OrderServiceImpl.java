package com.jemmy.order.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jemmy.common.support.BaseService;
import com.jemmy.common.vo.order.order.PagingItem;
import com.jemmy.common.vo.order.order.PagingVo;
import com.jemmy.order.mapper.OrderMapper;
import com.jemmy.order.model.Order;
import com.jemmy.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author Jemmy
 */
@Service
@Slf4j
public class OrderServiceImpl extends BaseService<Order,OrderMapper> implements OrderService {


    @Override
    public PagingVo paging(Long uid, Byte status, Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        Page<Order> orders=mapper.paging(uid,status);
        PagingVo result=new PagingVo();
        long count=orders.getTotal();
        if(count>0){
            result.setItems(orders.stream().map(item->{
                PagingItem newItem=new PagingItem();
                BeanUtils.copyProperties(item,newItem);
                return newItem;
            }).collect(Collectors.toList()));
        }

        return result;
    }

}
