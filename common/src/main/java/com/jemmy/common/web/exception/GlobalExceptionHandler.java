package com.jemmy.common.web.exception;


import com.jemmy.common.web.MVCResultMsg;
import com.jemmy.common.web.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

/**
 * 统一异常处理
 * Created by Jemmy on 2017/3/27.
 */
@RestControllerAdvice
@SuppressWarnings("unused")
public class GlobalExceptionHandler {

    private Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理本服务的404错误
     * @param ex
     * @return
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MVCResultMsg<String> notFoundException(NoHandlerFoundException ex){

        MVCResultMsg<String> result=new MVCResultMsg<>();
        result.setCode(ResultCode.FAIL);
        result.setMessage("接口不存在");
        return result;
    }


    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public MVCResultMsg<String> methodException(HttpRequestMethodNotSupportedException ex){
        MVCResultMsg<String> result=new MVCResultMsg<>();
        result.setCode(ResultCode.FAIL);
        result.setMessage("不支持的请求方法");
        return result;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MVCResultMsg<String> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        logger.error("{}",ex.getMessage());
        MVCResultMsg<String> result=new MVCResultMsg<>();
        result.setCode(ResultCode.FAIL);
        result.setMessage(ex.getMessage());
        return result;
    }

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MVCResultMsg<String> bindExcepion(BindException ex){
        logger.error("{}",ex.getMessage());
        MVCResultMsg<String> result=new MVCResultMsg<>();
        result.setCode(ResultCode.FAIL);
        BindingResult bindingResult=ex.getBindingResult();

        FieldError fieldError=bindingResult.getFieldError();
        result.setMessage(fieldError.getField()+":"+ fieldError.getDefaultMessage());
        return result;
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MVCResultMsg<String> missingServletRequestParameterException(MissingServletRequestParameterException ex){
        logger.error("{}",ex.getMessage());
        MVCResultMsg<String> result=new MVCResultMsg<>();
        result.setCode(ResultCode.FAIL);
        result.setMessage(ex.getParameterName()+":"+ ex.getMessage());
        return result;
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MVCResultMsg<String> constraintViolationExcepion(ConstraintViolationException ex){
        logger.error("{}",ex.getMessage());
        MVCResultMsg<String> result=new MVCResultMsg<>();
        result.setCode(ResultCode.FAIL);
        ConstraintViolation[] violations=ex.getConstraintViolations().toArray(new ConstraintViolation[]{});
        String first=violations[0].getPropertyPath().toString();
        result.setMessage(first.substring(first.lastIndexOf(".")+1)+":"+ violations[0].getMessage());
        return result;
    }


    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public MVCResultMsg<String> accessDeniedException(AccessDeniedException ex){
        MVCResultMsg<String> result=new MVCResultMsg<>();
        result.setCode(ResultCode.FAIL);
        result.setMessage("未授权");
        return result;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public MVCResultMsg<String> exceptionHandler(Exception ex){
        MVCResultMsg<String> result=new MVCResultMsg<>();
        result.setCode(ResultCode.FAIL);
        result.setMessage("服务器产生了不可预知的错误,请稍后重试");
        logger.error("发生错误:{}",ex.getMessage());
        return result;
    }
}
