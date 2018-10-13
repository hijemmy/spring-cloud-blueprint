package com.jemmy.user.controller;

import com.jemmy.common.io.user.user.RegisterIO;
import com.jemmy.common.vo.user.user.UserInfoVo;
import com.jemmy.common.web.MVCResultMsg;
import com.jemmy.common.web.ResultCode;
import com.jemmy.user.UserException;
import com.jemmy.user.anotation.JSONRequestMapping;
import com.jemmy.user.service.UserService;
import com.jemmy.user.vo.user.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/info")
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

