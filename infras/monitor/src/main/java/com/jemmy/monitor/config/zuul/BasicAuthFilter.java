package com.jemmy.monitor.config.zuul;

import com.fasterxml.jackson.core.Base64Variants;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@ConfigurationProperties("spring.boot.admin.auth")
public class BasicAuthFilter extends ZuulFilter implements InitializingBean {

    private String username;
    private String password;
    private String encodedAuth;

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(HttpHeaders.AUTHORIZATION, encodedAuth);
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String auth = username + ":" + password;
        encodedAuth = "Basic "
                + Base64Variants.MIME_NO_LINEFEEDS.encode(auth.getBytes(StandardCharsets.US_ASCII));
    }
}