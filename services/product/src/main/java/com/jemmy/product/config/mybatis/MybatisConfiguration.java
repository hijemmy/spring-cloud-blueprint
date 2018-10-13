package com.jemmy.product.config.mybatis;

import com.jemmy.common.mapper.RootMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.jemmy.product.mapper"},markerInterface = RootMapper.class)
@ComponentScan(basePackages = {"com.jemmy.common.mybatis"})
public class MybatisConfiguration {

}
