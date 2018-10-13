package com.jemmy.order.config.security;

import java.util.Map;

public class JwtAccessTokenConverter extends org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter {

    public Map<String, Object> decode(String token){
        return super.decode(token);
    }
}
