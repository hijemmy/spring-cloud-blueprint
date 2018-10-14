package com.jemmy.product.mapper;

import com.github.pagehelper.Page;
import com.jemmy.common.mybatis.mapper.RootMapper;
import com.jemmy.product.model.Product;
import org.apache.ibatis.annotations.Select;

public interface ProductMapper extends RootMapper<Product> {

    @Select("select * from product")
    Page<Product> findAllByPage();
}