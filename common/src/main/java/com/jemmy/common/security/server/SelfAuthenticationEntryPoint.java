package com.jemmy.common.security.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

import static com.jemmy.common.base.enums.ErrorCodeEnum.GL99990401;

/**
 * @author Jemmy
 */
public class SelfAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private ObjectMapper objectMapper;

    public SelfAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        MvcResult<String> result=MvcResultBuilder.wrap(GL99990401.code(),GL99990401.msg());

        response.setStatus(HttpStatus.OK.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(new String(objectMapper.writeValueAsString(result).getBytes(), Charset.forName("utf-8")));
        response.flushBuffer();
    }
}
