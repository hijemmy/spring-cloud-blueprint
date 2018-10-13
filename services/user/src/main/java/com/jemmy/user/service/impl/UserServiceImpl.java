package com.jemmy.user.service.impl;

import com.jemmy.common.security.Pbkdf2PasswordEncoder;
import com.jemmy.common.vo.user.user.UserInfoVo;
import com.jemmy.user.UserException;
import com.jemmy.user.mapper.UserMapper;
import com.jemmy.user.model.User;
import com.jemmy.user.service.UserService;
import com.jemmy.user.vo.user.RegisterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Pbkdf2PasswordEncoder passwordEncoder;


    @Override
    public UserInfoVo selectById(Long id) {
        User user=userMapper.selectByPrimaryKey(id);
        UserInfoVo result=null;
        if(user!=null){
            result=new UserInfoVo();
            result.setId(user.getId());
            result.setUsername(user.getName());
        }
        return result;
    }

    @Override
    public User findUserByUserame(String userName) {
        User user=new User();
        user.setName(userName);
        return userMapper.selectOne(user);
    }

    @Override
    public RegisterVo register(String username, String password) throws UserException {
        return null;
    }

}
