package com.jemmy.user.config.security;

import com.jemmy.common.web.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *拦截处理/oauth/token返回值的格式
 * 并在登录时,添加登录id
 */
@ControllerAdvice
@Slf4j
public class OAuth2TokenResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final static String accessTokenUrl="/oauth/token";

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.hasMethodAnnotation(RequestMapping.class)&&CollectionUtils.contains(Arrays.asList(returnType.getMethodAnnotation(RequestMapping.class).value()).iterator(),accessTokenUrl);
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if(body instanceof DefaultOAuth2AccessToken){
            Map<String, Object> result = new HashMap<>();
            HashMap<String,Object> data = new HashMap<>();
                DefaultOAuth2AccessToken newBody = (DefaultOAuth2AccessToken) body;
                data.put("access_token",newBody.getValue());
                data.put("refresh_token",newBody.getRefreshToken().getValue());
                Set<String> scope = newBody.getScope();
                if (scope != null && !scope.isEmpty()) {
                    StringBuilder scopes = new StringBuilder();
                    for (String s : scope) {
                        Assert.hasLength(s, "Scopes cannot be null or empty. Got " + scope + "");
                        scopes.append(s);
                        scopes.append(" ");
                    }
                    data.put("scope",scopes.toString());
                }
                data.put("token_type",newBody.getTokenType());
                data.put("expires_in",newBody.getExpiresIn());
                result.put("message", "刷新令牌成功");
                data.putAll(newBody.getAdditionalInformation());
                result.put("code", ResultCode.SUCCESS);
                result.put("data", data);
                return result;
        }
        return body;
    }
}
