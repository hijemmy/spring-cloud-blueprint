package com.jemmy.api.config.zuul.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 *  对于不限的URL请求,删除其认证头
 */
@Component
@Slf4j
public class OAuthTokenRemovePreFilter extends ZuulFilter {

    private static final String authHeader="authorization";
    @Value("${com.jemmy.api.urls.permitall}")
    private String[] permitAllUrls;

    private Set<String> permitAllUrlSet;

    @PostConstruct
    public void init(){
        permitAllUrlSet=new HashSet<>(Arrays.asList(permitAllUrls));
    }

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
        return permitAllUrlSet.contains(context.getRequest().getRequestURI());
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context=RequestContext.getCurrentContext();
        Set<String> ignoredHaders=(Set<String> )context.get(ProxyRequestHelper.IGNORED_HEADERS);
        //todo 仅当token无效时,才将其清除;如果有效,则保留
        log.debug("清空无效header");
        ignoredHaders.add(authHeader);
        context.put(ProxyRequestHelper.IGNORED_HEADERS,ignoredHaders);
        return null;
    }
}
