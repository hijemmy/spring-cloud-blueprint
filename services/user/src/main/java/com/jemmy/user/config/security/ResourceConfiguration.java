package com.jemmy.user.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jemmy.common.security.SelfAccessDeniedHandler;
import com.jemmy.common.security.SelfAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@ConditionalOnWebApplication
public class ResourceConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private ObjectMapper objectMapper;
    @Value("${com.jemmyweb.user.urls.permitall}")
    private String[] urls;


    /**
     * 自定义访问资源时的错误处理响应
     * @param resources
     * @throws Exception
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.accessDeniedHandler(new SelfAccessDeniedHandler(objectMapper)).authenticationEntryPoint(new SelfAuthenticationEntryPoint(objectMapper));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(urls).permitAll()
                .anyRequest().authenticated();
    }
}
