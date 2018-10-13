package com.jemmy.user.config.mybatis;

import com.jemmy.common.mapper.RootMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = "com.jemmy.user.mapper",markerInterface = RootMapper.class)
@ComponentScan("com.jemmy.common.mybatis")
public class MybatisConfiguration {

}
