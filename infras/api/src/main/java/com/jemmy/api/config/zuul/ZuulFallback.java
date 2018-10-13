package com.jemmy.api.config.zuul;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jemmy.common.web.MVCResultMsg;
import com.jemmy.common.web.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * 当各微服务挂掉时，用于统一处理
 * Created by Jemmy on 2017-09-14.
 */
@Component
public class ZuulFallback implements FallbackProvider {

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(Throwable cause) {
        return response(cause);
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return response(null);
    }

    /**
     * hystrix发生回退时,均认为业务微服务不可用.
     * 如果有明确的异常原因,则予以反馈;否则直接靠知"服务器错误,正在处理中"
     * @param cause
     * @return
     */
    private ClientHttpResponse response(Throwable cause){
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.SERVICE_UNAVAILABLE;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.SERVICE_UNAVAILABLE.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                MVCResultMsg<String> result=new MVCResultMsg<>();
                result.setCode(ResultCode.FAIL);
                if(null==cause)
                    result.setMessage("服务器错误，正在处理中");
                else
                    result.setMessage(cause.getMessage());
                return new ByteArrayInputStream(objectMapper.writeValueAsString(result).getBytes(Charset.forName("utf-8")));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers=new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return headers;
            }
        };
    }
}
