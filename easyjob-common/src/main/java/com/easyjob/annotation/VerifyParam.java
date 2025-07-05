package com.easyjob.annotation;

import com.easyjob.enums.VerifyRegexEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyParam {

    // 基本校验
    int min() default -1;
    int max() default -1;
    boolean required() default false;

    // 正则是否要校验
    VerifyRegexEnum regex() default VerifyRegexEnum.WO;
}
