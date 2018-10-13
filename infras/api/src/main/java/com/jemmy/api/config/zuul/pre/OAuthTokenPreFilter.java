package com.jemmy.api.config.zuul.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

/**
 *  对于获取token的请求,由网关代理请求
 */
@Component
public class OAuthTokenPreFilter extends ZuulFilter {
    private Logger logger=LoggerFactory.getLogger(OAuthTokenPreFilter.class);

    private String tokenUrl="/oauth/token";

    @Autowired
    ResourceServerProperties properties;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER+2;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context=RequestContext.getCurrentContext();
        boolean userService=context.containsKey(SERVICE_ID_KEY)&&"user".equals(context.get(SERVICE_ID_KEY).toString());
        boolean authUrl=context.containsKey(REQUEST_URI_KEY)&&(context.get(REQUEST_URI_KEY).toString().endsWith(tokenUrl));
        return userService&&authUrl;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context=RequestContext.getCurrentContext();
        HttpServletRequest request=context.getRequest();
        String authHeader="Authorization";
        String basicHeaderValue="Basic ";
        String header = request.getHeader(authHeader);
        if (header == null || !header.startsWith(basicHeaderValue)) {
            logger.debug("获取或刷新令牌,需要加basic 认证头部");
            String authValue=new String(Base64.encode("app:123456".getBytes()));
            context.getZuulRequestHeaders().put(authHeader,basicHeaderValue+authValue);
        }
        return null;
    }
}
