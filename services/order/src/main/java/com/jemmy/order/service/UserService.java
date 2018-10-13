package com.jemmy.order.service;


import com.jemmy.common.vo.user.user.UserInfoVo;
import com.jemmy.common.web.MVCResultMsg;
import com.jemmy.common.web.ResultCode;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 由Feign完成对auth服务的调用
 * @author Jemmy
 */
@FeignClient(name = "user",fallbackFactory = AuthServiceFallbackFactory.class,primary = false)
public interface UserService {

    @RequestMapping(path = "/user/info",method = RequestMethod.GET)
    MVCResultMsg<UserInfoVo> info(@RequestParam(name = "uid") Long uid);
}

@Component
class AuthServiceFallbackFactory implements FallbackFactory<UserService>{

    @Override
    public UserService create(Throwable cause) {
        return new UserService() {
            @Override
            public MVCResultMsg<UserInfoVo> info(Long uid) {
                //FeignException exception=new FeignException(cause);
                MVCResultMsg<UserInfoVo> result=new MVCResultMsg<>();
                result.setCode(ResultCode.FAIL);
                result.setMessage("无法获取用户信息");
                result.setData(null);
                return result;
            }
        };
    }
}