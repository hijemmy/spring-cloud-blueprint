package com.jemmy.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@ConditionalOnWebApplication
public class RequestLogAspect {
    private static Logger logger=LoggerFactory.getLogger(RequestLogAspect.class);

    @Autowired
    private HttpServletRequest request;

    /**
     * 请求日志切入点
     */
    @Pointcut("execution(public * com.jemmy.*.controller.*.*(..))")
    public void serviceStatistics(){}


    @Before("serviceStatistics()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //从request中获取http请求的url/请求的方法类型／响应该http请求的类方法／IP地址／请求中的参数
        logger.info("Url={},Method={},Ip={},ClassMethod={},Args={}",request.getRequestURI(),request.getMethod(),request.getRemoteAddr(),joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName(),joinPoint.getArgs());
    }
    @AfterReturning(returning = "object",pointcut = "serviceStatistics()")
    public void doAfter(Object object){
        logger.info("Response:{}",object);
    }

}
