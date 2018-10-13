package com.jemmy.product.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jemmy.common.vo.product.product.GetAllProductItemVo;
import com.jemmy.common.vo.product.product.GetAllProductVo;
import com.jemmy.product.mapper.ProductMapper;
import com.jemmy.product.model.Product;
import com.jemmy.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public GetAllProductVo findAllProduct(Integer page, Integer size) {
        GetAllProductVo result=new GetAllProductVo();
        PageHelper.startPage(page,size);
        Page<Product> products=productMapper.findAllByPage();
        long total=products.getTotal();
        result.setCount(total);

        if(total>0){
            result.setItems(products.getResult().stream().map(item->{
                GetAllProductItemVo newItem=new GetAllProductItemVo();
                BeanUtils.copyProperties(item,newItem);
                return newItem;
            }).collect(Collectors.toList()));
        }
        return result;
    }
}
