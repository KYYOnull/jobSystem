package com.easyjob.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) // 类 方法 参数
@Retention(RetentionPolicy.RUNTIME) // runtime
public @interface GlobalInterceptor {

    boolean checkParams() default true;

    boolean checkLogin() default  false; // app 中的大部分功能 无需登录 也可使用
}
