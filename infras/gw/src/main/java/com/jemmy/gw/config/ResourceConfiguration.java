package com.jemmy.gw.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceConfiguration extends ResourceServerConfigurerAdapter{


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.csrf().disable()
                    .authorizeRequests().antMatchers("/druid/**", "/pay/alipayCallback", "/swagger-ui.html","/swagger-ui.html/**", "/swagger-resources/**", "/*/v2/api-docs", "/api/applications")
                    .permitAll()
                .and()
                    .authorizeRequests()
                    .anyRequest().authenticated();

    }


}
