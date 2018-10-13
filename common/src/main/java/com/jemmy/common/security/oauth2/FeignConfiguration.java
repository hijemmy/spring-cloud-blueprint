package com.jemmy.common.security.oauth2;

import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;

@Configuration
public class FeignConfiguration {

    /**
     * 将OAuth认证信息放置请求头中,以备feign客户端使用
     * @param oAuth2ClientContext
     * @return
     */
    @Bean
    OAuth2FeignRequestInterceptor auth2FeignRequestInterceptor(OAuth2ClientContext oAuth2ClientContext){
        return new OAuth2FeignRequestInterceptor(oAuth2ClientContext,new ResourceOwnerPasswordResourceDetails());
    }

    /**
     * 在OAuth,hystrix.shareSecurityContext为真及hystrix隔离策略为THREAD时,注意token的正确传递
     * @return
     */
    /*@Bean("oAuth2ClientContext")
    @Primary
    public OAuth2ClientContext oAuth2ClientContext() {
        return new DefaultOAuth2ClientContext() {
            @Override
            public OAuth2AccessToken getAccessToken() {
                return Optional.ofNullable(super.getAccessToken())
                        .orElse(new DefaultOAuth2AccessToken(
                                ((OAuth2AuthenticationDetails) SecurityContextHolder
                                        .getContext().getAuthentication().getDetails())
                                        .getTokenValue()));
            }
        };
    }*/
}
