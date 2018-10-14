package com.jemmy.user.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jemmy.common.security.SelfAccessDeniedHandler;
import com.jemmy.common.security.SelfAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * 认证服务配置
 */
@Configuration
@EnableAuthorizationServer
@ConditionalOnWebApplication
@Slf4j
public class AuthorizationConfiguration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${com.jemmyweb.user.oauth.tokenValidateSeconds.access}")
    private Integer accessTokenValidateSeconds;

    @Value("${com.jemmyweb.user.oauth.tokenValidateSeconds.refresh}")
    private Integer refreshTokenValidateSeconds;

    @Value("${security.oauth2.resource.jwt.key-value}")
    private String jwtSign;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //使用内存存储
        clients.inMemory()
                //分配客户端账号
                .withClient("api")
                .secret("123456")
                //支持的授权类型
                .authorizedGrantTypes("refresh_token", "client_credentials")
                .authorities("ROLE_USER")
                .scopes("server")
            .and()
                //分配客户端账号
                .withClient("app")
                .secret("123456")
                //支持的授权类型
                .authorizedGrantTypes("refresh_token", "password")
                .authorities("ROLE_USER")
                .scopes("ui")
        ;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        //设置签名密钥
        jwtAccessTokenConverter.setSigningKey(jwtSign);
        return jwtAccessTokenConverter;
    }

    //使用JWT作为token
    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public TokenEnhancer tokenEnhancer(){
        TokenEnhancerChain tokenEnhancerChain=new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(selfTokenEnhancer(),jwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }

   @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(jwtTokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setTokenEnhancer(tokenEnhancer());
        defaultTokenServices.setAccessTokenValiditySeconds(accessTokenValidateSeconds);
        defaultTokenServices.setRefreshTokenValiditySeconds(refreshTokenValidateSeconds);
        return defaultTokenServices;
    }

    @Bean
    public TokenEnhancer selfTokenEnhancer(){
        return new SelfTokenEnhancer(objectMapper);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                //重新定义默认的oauth endpoint
                //.pathMapping("/oauth/token","/sso/token")
                .accessTokenConverter(jwtAccessTokenConverter())
                //配置以生效password模式
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .tokenEnhancer(tokenEnhancer())
                .exceptionTranslator(new SelfWebResponseExceptionTranslator())
                .tokenServices(tokenServices())
                ;
    }

    /**
     * 定义对token endpoint的限制
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").authenticationEntryPoint(new SelfAuthenticationEntryPoint(objectMapper))
                .accessDeniedHandler(new SelfAccessDeniedHandler(objectMapper));
    }


}
