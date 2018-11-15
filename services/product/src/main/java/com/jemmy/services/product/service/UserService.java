package com.jemmy.services.product.service;

import com.jemmy.apis.user.UserServiceFeignApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Jemmy
 */
@Service
@Slf4j
public class UserService {

    @Resource
    private UserServiceFeignApi userServiceFeignApi;

}
