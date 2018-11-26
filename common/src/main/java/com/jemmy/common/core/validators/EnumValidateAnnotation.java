package com.jemmy.common.core.validators;

import javax.validation.Payload;

public @interface EnumValidateAnnotation {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?>[] target() default {};
}
