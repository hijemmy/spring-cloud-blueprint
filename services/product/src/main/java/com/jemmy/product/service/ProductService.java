package com.jemmy.product.service;


import com.jemmy.product.vo.product.GetAllProductVo;

public interface ProductService {


    GetAllProductVo findAllProduct(Integer page, Integer size);

}
