package com.jemmy.services.edriven.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jemmy.common.security.core.SelfAccessDeniedHandler;
import com.jemmy.common.security.core.SelfAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author Jemmy
 */
@Configuration
@EnableResourceServer
public class ResourceConfiguration extends ResourceServerConfigurerAdapter{
    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new SelfAuthenticationEntryPoint(objectMapper))
                .and()
                .authorizeRequests().antMatchers("/pay/alipayCallback", "/druid/**", "/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/api/applications").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.accessDeniedHandler(new SelfAccessDeniedHandler(objectMapper)).authenticationEntryPoint(new SelfAuthenticationEntryPoint(objectMapper));
    }
}
