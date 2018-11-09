package com.jemmy.user.controller;

import com.jemmy.apis.user.UserServiceFeignApi;
import com.jemmy.apis.user.vo.user.UserInfoVo;
import com.jemmy.common.annotation.JSONRequestMapping;
import com.jemmy.common.web.MVCResultMsg;
import com.jemmy.common.web.ResultCode;
import com.jemmy.user.UserException;
import com.jemmy.user.io.user.RegisterIO;
import com.jemmy.user.service.UserService;
import com.jemmy.user.vo.user.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;

/**
 * @author Jemmy
 */
@RestController
@JSONRequestMapping("/user")
@Slf4j
public class UserController implements UserServiceFeignApi {

    @Autowired
    private UserService userService;

    @Override
    public MVCResultMsg<UserInfoVo> info(@Valid @Min(1) @RequestParam Long uid){
        MVCResultMsg<UserInfoVo> result=new MVCResultMsg<>();
        UserInfoVo userInfoVO=userService.selectById(uid);
        if(null==userInfoVO){
            result.setMessage("用户不存在");
            result.setCode(ResultCode.FAIL);
        }else {
            result.setCode(ResultCode.SUCCESS);
            result.setData(userInfoVO);
        }
        return result;
    }

    /**
     * 注册用户
     * @param io
     * @return
     */
    @PostMapping("/register")
    public MVCResultMsg<RegisterVo> register(@Validated RegisterIO io){
        MVCResultMsg<RegisterVo> result=new MVCResultMsg<>();
        try {
            RegisterVo vo=userService.register(io.getUsername(),io.getPassword());
            result.setCode(ResultCode.SUCCESS);
            result.setData(vo);
        } catch (UserException e) {
            result.setCode(ResultCode.FAIL);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}

