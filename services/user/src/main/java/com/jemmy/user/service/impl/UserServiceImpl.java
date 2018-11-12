package com.jemmy.user.service.impl;

import com.jemmy.apis.user.vo.user.UserInfoVo;
import com.jemmy.common.core.support.BaseService;
import com.jemmy.common.security.Pbkdf2PasswordEncoder;
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

/**
 * @author Jemmy
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseService<User> implements UserService {

    private Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private Pbkdf2PasswordEncoder passwordEncoder;


    @Override
    public UserInfoVo selectById(Long id) {
        User user=mapper.selectByPrimaryKey(id);
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
        return mapper.selectOne(user);
    }

    @Override
    public RegisterVo register(String username, String password) throws UserException {
        return null;
    }

}
