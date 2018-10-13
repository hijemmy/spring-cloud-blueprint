package com.jemmy.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jemmy.common.web.MVCResultMsg;
import com.jemmy.common.web.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.DefaultCorsProcessor;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CORS跨域请求统一控制
 * Created by Jemmy on 2017-09-08.
 */

@SuppressWarnings("unused")
public class CorsFilter extends org.springframework.web.filter.CorsFilter{
    private Logger logger= LoggerFactory.getLogger(CorsFilter.class);
    private static CorsConfiguration corsConfiguration;

    public static final int ORDER= SecurityProperties.DEFAULT_FILTER_ORDER-10000;
    static {
        corsConfiguration=new CorsConfiguration();
        corsConfiguration.setAllowCredentials(false);
        corsConfiguration.addAllowedHeader(HttpHeaders.AUTHORIZATION);
        corsConfiguration.addAllowedHeader(HttpHeaders.CONTENT_TYPE);
        corsConfiguration.addAllowedMethod(HttpMethod.POST);
        corsConfiguration.addAllowedMethod(HttpMethod.GET);
        corsConfiguration.addAllowedMethod(HttpMethod.OPTIONS);
    }

    private String cors;

    private ObjectMapper objectMapper;
    private List<RequestMatcher> ignoredPatterns;

    public CorsFilter(String cors,ObjectMapper objectMapper,String[] mvcEndpoints) {
        super(configurationSource());
        this.setCorsProcessor(new DefaultCorsProcessor(){
            @Override
            protected void rejectRequest(ServerHttpResponse response) throws IOException {
                rejectCorsRequst(response);
            }
        });
        this.cors=cors;
        this.objectMapper=objectMapper;
        if(null!=mvcEndpoints){
            this.ignoredPatterns= Arrays.stream(mvcEndpoints).map(endpoint->new AntPathRequestMatcher(endpoint, null)).collect(Collectors.toList());
        }
    }

    private void rejectCorsRequst(ServerHttpResponse response) throws IOException{
        MVCResultMsg<String> result=new MVCResultMsg<>();
        result.setCode(ResultCode.FAIL);
        result.setMessage("不允许的跨域请求");
        response.setStatusCode(HttpStatus.FORBIDDEN);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        response.getBody().write(objectMapper.writeValueAsBytes(result));
    }

    @PostConstruct
    private void initCors(){
        corsConfiguration.addAllowedOrigin(cors);
    }

    private static UrlBasedCorsConfigurationSource configurationSource(){
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfiguration);
        return source;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(StringUtils.hasText(cors) &&!CorsUtils.isCorsRequest(request)){
            if(!isManagementAPI(request)){
                logger.debug("与CORS请求的要求不相符,予以拒绝:{}",request.getRequestURI());
                rejectCorsRequst(new ServletServerHttpResponse(response));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isManagementAPI(HttpServletRequest request){
        if(ignoredPatterns==null||ignoredPatterns.isEmpty()) {
            return false;
        }
        return ignoredPatterns.stream().anyMatch(pattern->pattern.matches(request));
    }


}
