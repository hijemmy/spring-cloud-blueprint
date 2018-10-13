package com.jemmy.order.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jemmy.common.security.Pbkdf2PasswordEncoder;
import com.jemmy.common.security.SelfAccessDeniedHandler;
import com.jemmy.common.security.SelfAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
@ConditionalOnWebApplication
@RefreshScope
public class ResourceConfiguration extends ResourceServerConfigurerAdapter{

    @Autowired
    private ObjectMapper objectMapper;
    @Value("${com.jemmyweb.order.urls.permitall}")
    private String[] urls;

    @Value("${security.oauth2.resource.jwt.key-value}")
    private String signKey;

    @Autowired
    OAuth2ClientContext oAuth2ClientContext;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers(urls).permitAll()
                .anyRequest().authenticated();
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

    @Bean
    public Pbkdf2PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(512,1000,16);
    }
}
