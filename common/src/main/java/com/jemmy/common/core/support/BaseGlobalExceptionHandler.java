package com.jemmy.common.core.support;


import com.jemmy.common.base.enums.ErrorCodeEnum;
import com.jemmy.common.base.exception.BusinessException;
import com.jemmy.common.util.wrapper.MvcResult;
import com.jemmy.common.util.wrapper.MvcResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static com.jemmy.common.base.enums.ErrorCodeEnum.*;

/**
 * 统一异常处理
 *
 * @author Jemmy
 * @date 2017/3/27
 */
@RestControllerAdvice
@SuppressWarnings("unused")
@Slf4j
public class BaseGlobalExceptionHandler {

    /**
     * 处理本服务的404错误
     * @param ex
     * @return
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.OK)
    public MvcResult<String> notFoundException(NoHandlerFoundException ex){
        return MvcResultBuilder.wrap(GL9999404.code(),GL9999404.msg());
    }


    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.OK)
    public MvcResult<String> methodException(HttpRequestMethodNotSupportedException ex){
        return MvcResultBuilder.wrap(GL99994045.code(),GL99994045.msg());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    public MvcResult<String> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        log.error("{}",ex.getMessage());
        return MvcResultBuilder.wrap(GL99990100.code(),ex.getMessage());
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.OK)
    public MvcResult<String> bindExcepion(BindException ex){
        log.error("{}",ex.getMessage());

        BindingResult bindingResult=ex.getBindingResult();
        FieldError fieldError=bindingResult.getFieldError();
        return MvcResultBuilder.wrap(GL99990100.code(),fieldError.getField()+":"+ fieldError.getDefaultMessage());
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.OK)
    public MvcResult<String> missingServletRequestParameterException(MissingServletRequestParameterException ex){
        log.error("{}",ex.getMessage());
        return MvcResultBuilder.wrap(GL99990100.code(),ex.getParameterName()+":"+ ex.getMessage());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    public MvcResult<String> constraintViolationExcepion(ConstraintViolationException ex){
        log.error("{}",ex.getMessage());
        ConstraintViolation[] violations=ex.getConstraintViolations().toArray(new ConstraintViolation[]{});
        String first=violations[0].getPropertyPath().toString();
        return MvcResultBuilder.wrap(GL99990100.code(),first.substring(first.lastIndexOf(".")+1)+":"+ violations[0].getMessage());
    }


    /**
     * 参数非法异常.
     *
     * @param e the e
     *
     * @return the wrapper
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MvcResult illegalArgumentException(IllegalArgumentException e) {
        log.error("参数非法异常={}", e.getMessage(), e);
        return MvcResultBuilder.wrap(ErrorCodeEnum.GL99990100.code(), e.getMessage());
    }

    /**
     * 业务异常.
     *
     * @param e the e
     *
     * @return the wrapper
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public MvcResult businessException(BusinessException e) {
        log.error("业务异常={}", e.getMessage(), e);
        return MvcResultBuilder.wrap(e.getCode() == 0 ? MvcResult.ERROR_CODE : e.getCode(), e.getMessage());
    }

    /**
     * 无权限访问.
     *
     * @param e the e
     *
     * @return the wrapper
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public MvcResult unAuthorizedException(AccessDeniedException e) {
        log.error("业务异常={}", e.getMessage(), e);
        return MvcResultBuilder.wrap(ErrorCodeEnum.GL99990401.code(), ErrorCodeEnum.GL99990401.msg());
    }
}
