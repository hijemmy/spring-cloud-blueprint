package com.jemmy.user.service;

import com.jemmy.apis.user.vo.user.UserInfoVo;
import com.jemmy.user.UserException;
import com.jemmy.user.model.User;
import com.jemmy.user.vo.user.RegisterVo;

public interface UserService {

    UserInfoVo selectById(Long id);

    User findUserByUserame(String userName);

    /**
     * 注册用户
     * @param username 用户名称
     * @param password 密码
     * @return
     * @throws UserException
     */
    RegisterVo register(String username, String password) throws UserException;

}
