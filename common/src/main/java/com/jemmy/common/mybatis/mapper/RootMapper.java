package com.jemmy.common.mybatis.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * Created by Jemmy on 2017/7/4.
 */
public interface RootMapper<T> extends Mapper<T>,MySqlMapper<T> {
}
