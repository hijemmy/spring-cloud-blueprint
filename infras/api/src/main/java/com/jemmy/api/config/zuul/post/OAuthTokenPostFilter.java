package com.jemmy.api.config.zuul.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.REQUEST_URI_KEY;

/**
 * 对oauth的处理,放置于auth服务本地处理,以降低服务之间的耦合性
 */
//@Component
@Deprecated
public class OAuthTokenPostFilter extends ZuulFilter {

    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger=LoggerFactory.getLogger(OAuthTokenPostFilter.class);

    private String tokenUrl="/oauth/token";

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext context=RequestContext.getCurrentContext();
        return context.containsKey(REQUEST_URI_KEY)&&(context.get(REQUEST_URI_KEY).toString().endsWith(tokenUrl))&&context.getResponseStatusCode()==HttpStatus.OK.value();
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context=RequestContext.getCurrentContext();
        InputStreamReader inReader=new InputStreamReader(context.getResponseDataStream());
        try {
            final String responseData=CharStreams.toString(inReader);
            HashMap data=objectMapper.readValue(responseData,HashMap.class);
            HashMap<String,Object> result=new HashMap<>();
            result.put("code","0");
            result.put("msg",null);
            result.put("data",data);
            context.setResponseBody(objectMapper.writeValueAsString(result));
        } catch (IOException e) {
            throw new ZuulException(e,"读取origin响应结果错误",HttpStatus.INTERNAL_SERVER_ERROR.value(),"流错误");
        }
        return null;
    }
}
