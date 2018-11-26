package com.jemmy.common.core.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Jemmy
 */
public class EnumValidator implements ConstraintValidator<EnumValidateAnnotation,String> {
    Class<?>[] classes;
    @Override
    public void initialize(EnumValidateAnnotation constraintAnnotation) {
        classes=constraintAnnotation.target();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(classes.length>0){
            for(Class<?> clazz:classes){
                try {
                    if(clazz.isEnum()){
                        Object[] objects=clazz.getEnumConstants();
                        Method method=clazz.getMethod("name");
                        for(Object obj:objects){
                            Object code=method.invoke(obj,null);
                            if(value.equals(code.toString())){
                                return true;
                            }
                        }
                    }
                    return false;

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
        return false;
    }
}
