package com.jemmy.product.controller;

import com.jemmy.common.annotation.JSONRequestMapping;
import com.jemmy.common.vo.product.product.GetAllProductVo;
import com.jemmy.common.web.MVCResultMsg;
import com.jemmy.common.web.ResultCode;
import com.jemmy.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jemmy
 */
@RestController
@JSONRequestMapping("/product")
@Slf4j
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     *  获取所有上架有效产品
     * @paramio
     * @return
     */
    @GetMapping("/getAllProduct")
    public MVCResultMsg<GetAllProductVo> getAllProduct(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer size){
        MVCResultMsg<GetAllProductVo> result=new MVCResultMsg<>();
        result.setCode(ResultCode.SUCCESS);
        result.setData(productService.findAllProduct(page, size));
        return result;
    }



}