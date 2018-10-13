package com.jemmy.order.config.mybatis;

import com.jemmy.common.mapper.RootMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = {"com.jemmy.order.mapper"})
@ComponentScan(basePackages = {"com.jemmy.common.mybatis"})
public class MybatisConfiguration {

}
