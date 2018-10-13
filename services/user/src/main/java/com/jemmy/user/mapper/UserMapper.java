package com.jemmy.user.mapper;

import com.jemmy.common.mapper.RootMapper;
import com.jemmy.user.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

public interface UserMapper extends RootMapper<User> {

}