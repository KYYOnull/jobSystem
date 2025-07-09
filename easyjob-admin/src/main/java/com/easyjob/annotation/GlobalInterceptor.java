package com.easyjob.annotation;

import com.easyjob.enums.PermissionCodeEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD}) // 类 方法 参数
@Retention(RetentionPolicy.RUNTIME) // runtime
public @interface GlobalInterceptor {

    boolean checkParams() default true;

    boolean checkLogin() default true;

    PermissionCodeEnum permissionCode() default PermissionCodeEnum.NO_PERMISSION;

}
