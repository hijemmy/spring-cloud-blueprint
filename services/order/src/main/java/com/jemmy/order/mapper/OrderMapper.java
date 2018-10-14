package com.jemmy.order.mapper;

import com.github.pagehelper.Page;
import com.jemmy.common.mapper.RootMapper;
import com.jemmy.order.model.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface OrderMapper extends RootMapper<Order> {

    @Select("select * from `order` where state=#{status} and uid=#{uid}" )
    Page<Order> paging(@Param("uid")Long uid, @Param("status") Byte status);
}