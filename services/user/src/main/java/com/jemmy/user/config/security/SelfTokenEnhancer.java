package com.jemmy.user.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jemmy.user.model.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;
@Slf4j
public class SelfTokenEnhancer implements TokenEnhancer {
    private ObjectMapper objectMapper;

    public SelfTokenEnhancer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String,Object> extraInfor=new HashMap<>();
        Object principal=authentication.getPrincipal();
        if(principal instanceof UserPrincipal){
            log.debug("登录情况下,增强访问令牌");
            UserPrincipal userPrincipal=(UserPrincipal)principal;
            extraInfor.put("id",userPrincipal.getUsername());
            extraInfor.put("username",userPrincipal.getMobile());
        }
        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(extraInfor);
        return accessToken;
    }
}
