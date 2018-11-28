package com.jemmy.common.security.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

public class SelfAccessDeniedHandler implements AccessDeniedHandler {
    private ObjectMapper objectMapper;

    public SelfAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        MvcResult<String> result=MvcResultBuilder.wrap(HttpServletResponse.SC_FORBIDDEN,accessDeniedException.getMessage());
        response.setHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().write(new String(objectMapper.writeValueAsString(result).getBytes(), Charset.forName("utf-8")));
        response.flushBuffer();
    }
}
