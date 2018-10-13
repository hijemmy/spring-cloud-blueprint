package com.jemmy.api.config.zuul.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import io.jmnarloch.spring.cloud.ribbon.support.RibbonFilterContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

/**
 * 灰度发布、蓝绿发布、金丝雀发布
 *
 * @author Jemmy
 */
@Component
public class RibbonCanaryIruleFilter extends ZuulFilter {
    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        // a filter has already forwarded
        // a filter has already determined serviceId
        return !context.containsKey(FORWARD_TO_KEY)
            && !context.containsKey(SERVICE_ID_KEY);
    }

    /**
     * 金丝雀发布:
     * 选择header里带有foo=1的全部路由到金丝雀服务节点上，其他的还走原来的老版本
     * PS: 如果IRule配置了均衡负载，此处就失效
     * @return
     */
    @Override
    public Object run() {
        RibbonFilterContextHolder.clearCurrentContext();
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String version=request.getHeader("X-API-VERSION");
        if (version!= null) {
            RibbonFilterContextHolder.getCurrentContext()
                .add("version", version);
        }  else {
            RibbonFilterContextHolder.getCurrentContext()
                .add("version", "1.0");
        }
        return null;
    }

}
