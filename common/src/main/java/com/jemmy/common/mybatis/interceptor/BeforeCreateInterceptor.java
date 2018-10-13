package com.jemmy.common.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.*;

/**
 * @author Jemmy
 */
@Component
@Intercepts(@Signature(type = Executor.class, method = BeforeCreateInterceptor.EXECUTOR_METHOD_UPDATE, args = {MappedStatement.class, Object.class}))
public class BeforeCreateInterceptor implements Interceptor {

    private static final Logger logger=LoggerFactory.getLogger(BeforeCreateInterceptor.class);

    static final String EXECUTOR_METHOD_UPDATE="update";
    private final String FIELD_CREATEAT="create_time";
    private final String FIELD_UPDATEAT="update_time";
    private final String FIELD_EXPIREDATE="expire_date";
    private final String MAPPER_S_ORDER=".*\\.OrderMapper\\..*";
    private final List<String> targets;

    {
        targets=new ArrayList<>();
        targets.add(FIELD_CREATEAT);
        targets.add(FIELD_UPDATEAT);
        targets.add(FIELD_EXPIREDATE);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof Executor && invocation.getMethod().getName().equals(EXECUTOR_METHOD_UPDATE)) {
            return invokeUpdate(invocation);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


    private Object invokeUpdate(Invocation invocation) throws Exception {
        // 获取第一个参数
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        // mybatis的参数对象
        Object paramObj = invocation.getArgs()[1];
        switch (ms.getSqlCommandType()){
            case INSERT:

                if (paramObj == null) {
                    return invocation.proceed();
                }
                // 只拦截处理参数为基于model传值的
                if (ms.getParameterMap().getType()==null) {
                    return invocation.proceed();
                }
                processParamForInsert(paramObj,ms.getId());
                return invocation.proceed();
            // mybatis的参数对象
            case UPDATE:
                if (paramObj == null) {
                    return invocation.proceed();
                }
                // 只拦截处理参数为基于model传值的
                if (ms.getParameterMap().getType()==null) {
                    return invocation.proceed();
                }

                processParamForUpdate(paramObj,ms.getId());
                return invocation.proceed();
            default:
                return invocation.proceed();
        }
    }


    private void processParamForInsert(Object parameterObject,String msId) {
        // 处理参数对象  如果是 map 且map的key 中没有 tenantId，添加到参数map中
        // 如果参数是bean，反射设置值
       Class clazz=parameterObject.getClass();
       Date now=Date.from(Instant.now());
        targets.stream().forEach(field->{
            try {
                PropertyDescriptor fd=BeanUtils.getPropertyDescriptor(clazz,field);
                if (fd != null && fd.getReadMethod() != null && fd.getWriteMethod() != null) {
                    Object value = fd.getReadMethod().invoke(parameterObject);
                    if (value == null) {
                        switch (field){
                            case FIELD_CREATEAT:
                            case FIELD_UPDATEAT:
                                fd.getWriteMethod().invoke(parameterObject, now);
                                break;
                            case FIELD_EXPIREDATE:
                                boolean isOrder=msId.matches(MAPPER_S_ORDER);
                                if(isOrder){
                                    PropertyDescriptor dfCreate=BeanUtils.getPropertyDescriptor(clazz,FIELD_CREATEAT);
                                    Date createTime=(Date)dfCreate.getReadMethod().invoke(parameterObject);
                                    fd.getWriteMethod().invoke(parameterObject, Date.from(createTime.toInstant().plusSeconds(180)));
                                }

                                break;
                            default:

                        }

                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
               logger.error(e.getMessage());
            }

        });

    }

    private void processParamForUpdate(Object parameterObject,String msId) {
        // 处理参数对象  如果是 map 且map的key 中没有 tenantId，添加到参数map中
        // 如果参数是bean，反射设置值
        Class clazz=parameterObject.getClass();
        Date now=Date.from(Instant.now());
        targets.forEach(field->{
            try {
                PropertyDescriptor fd=BeanUtils.getPropertyDescriptor(clazz,field);
                if (fd != null && fd.getReadMethod() != null && fd.getWriteMethod() != null) {
                    Object value = fd.getReadMethod().invoke(parameterObject);
                    if (value == null&&field.equals(FIELD_UPDATEAT)) {
                         fd.getWriteMethod().invoke(parameterObject, now);
                    }
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error(e.getMessage());
            }

        });

    }

}
