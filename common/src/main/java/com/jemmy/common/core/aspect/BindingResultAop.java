/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：BindingResultAop.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.jemmy.common.core.aspect;

import com.jemmy.common.core.annotation.ValidateAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * The class Binding result aop.
 *
 * @author paascloud.net@gmail.com
 */
@Component
@Aspect
@Slf4j
public class BindingResultAop {

	/**
	 * Do after.
	 *
	 * @param joinPoint the join point
	 */
	@Before("@annotation(validation)")
	public void doBefore(final JoinPoint joinPoint,ValidateAnnotation validation) {
		String methodName = joinPoint.getSignature().getName();
		Object target = joinPoint.getTarget();
		//得到拦截的方法
		Method method = getMethodByClassAndName(target.getClass(), methodName);
		Object[] objects = joinPoint.getArgs();
		//方法的参数
		assert method != null;
		if (validation != null) {
			BindingResult bindingResult = null;
			for (Object arg : objects) {
				if (arg instanceof BindingResult) {
					bindingResult = (BindingResult) arg;
					break;
				}
			}
			if (bindingResult != null && bindingResult.hasErrors()) {
				FieldError fieldError=bindingResult.getFieldError();
				String errorInfo = fieldError.getField()+fieldError.getDefaultMessage();
				throw new IllegalArgumentException(errorInfo);
			}
		}
	}

	/**
	 * 根据类和方法名得到方法
	 */
	private Method getMethodByClassAndName(Class c, String methodName) {
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				return method;
			}
		}
		return null;
	}
}
