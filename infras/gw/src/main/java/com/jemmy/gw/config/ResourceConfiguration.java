package com.jemmy.gw.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jemmy.common.security.SelfAccessDeniedHandler;
import com.jemmy.common.security.SelfAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoint;
import org.springframework.boot.actuate.endpoint.mvc.MvcEndpoints;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

//@Configuration
//@EnableResourceServer
//@RefreshScope
public class ResourceConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    private ObjectMapper objectMapper;
    @Value("${com.jemmy.gw.urls.permitall}")
    private String[] urls;
    @Value("${com.jemmy.headers.cors}")
    private String cors;

    @Value("${security.oauth2.resource.jwt.key-value}")
    private String signKey;

    @Autowired(required = false)
    private MvcEndpoints mvcEndpoints;

    private String[] mgmUrls;

    @PostConstruct
    public void init(){
        if(mvcEndpoints!=null&&!mvcEndpoints.getEndpoints().isEmpty()){
            mgmUrls=mvcEndpoints.getEndpoints().stream().map(MvcEndpoint::getPath).toArray(String[]::new);
        }else {
            mgmUrls=new String[]{};
        }
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .headers().frameOptions().disable()
            .and()
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
            .and()
            .authorizeRequests().antMatchers("/pay/alipayCallback", "/druid/**", "/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/api/applications").permitAll()
            .anyRequest().authenticated();
        http.httpBasic().disable();
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers(urls).permitAll()
                .antMatchers(mgmUrls).permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(new CorsFilter(cors,objectMapper,mgmUrls),ChannelProcessingFilter.class);
    }

    //使用JWT作为token
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //设置签名密钥
        jwtAccessTokenConverter.setSigningKey(signKey);
        return jwtAccessTokenConverter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(jwtTokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.accessDeniedHandler(new SelfAccessDeniedHandler(objectMapper)).authenticationEntryPoint(new SelfAuthenticationEntryPoint(objectMapper));
    }
}
