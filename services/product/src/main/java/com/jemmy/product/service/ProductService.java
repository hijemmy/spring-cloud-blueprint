package com.jemmy.product.service;

import com.jemmy.common.vo.product.product.GetAllProductVo;

public interface ProductService {


    GetAllProductVo findAllProduct(Integer page,Integer size);

}
