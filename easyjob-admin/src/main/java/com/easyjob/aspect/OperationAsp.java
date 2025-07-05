package com.easyjob.aspect;

import com.easyjob.annotation.GlobalInterceptor;
import com.easyjob.annotation.VerifyParam;
import com.easyjob.enums.ResponseCodeEnum;
import com.easyjob.exception.BusinessException;
import com.easyjob.utils.StringTools;
import com.easyjob.utils.VerifyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component("opAsp") // 交给 spring
public class OperationAsp {

    private Logger logger= LoggerFactory.getLogger(OperationAsp.class);

    // 常见校验类型
    private static final String[] BASE_TYPE_LST= new String[]{
            "java.lang.String",
            "java.lang.Integer",
            "java.lang.Long"
    };


    // Before 中指定切点
    @Before("@annotation(com.easyjob.annotation.GlobalInterceptor)")
    public void interceptorDoBefore(JoinPoint jp) {

        logger.info(jp.getArgs().toString());

        Object[] args = jp.getArgs(); // ses phone ...
        Method method = ((MethodSignature) jp.getSignature()).getMethod();
        GlobalInterceptor interceptor = method.getAnnotation(GlobalInterceptor.class); // login 的注解
        if(interceptor==null) return;
        if(interceptor.checkParams()){

            validateParams(method, args);
        }

    }

    // 切点上的校验过程
    private void validateParams(Method method, Object[] args){

        // 每个参数的规则不同 对每个参数单独加注解
        // aop 拿到注解传参 从而实现注解内部逻辑 传参是枚举是被限定操作的
        Parameter[] paramLst = method.getParameters();
        for(int i=0;i<paramLst.length;i++){

            Parameter paramItem = paramLst[i]; // 方法签名参数
            Object val = args[i]; // 真值

            VerifyParam verStuff = paramItem.getAnnotation(VerifyParam.class); // 该参数的校验填充信息
            if(verStuff==null) continue; // 未加注解

            String type = paramItem.getParameterizedType().getTypeName(); // 被注解的参数
            if(ArrayUtils.contains(BASE_TYPE_LST, type)){ // 传入的是基本类型 进行基本校验

                checkBaseVal(val, verStuff);
            }else { // 校验实体

                checkObjVal(paramItem, val);
            }
        }

    }

    // 基本类型的校验分支 val为真值 verStuff为校验填充规则
    private void checkBaseVal(Object val, VerifyParam verStuff) { // 真值 和 注解设置

        Boolean isEmpty = val==null || StringTools.isEmpty(val.toString()); // 空对象则直接 true 无需toString
        Integer len = val==null? 0 : val.toString().length();

        if(isEmpty && verStuff.required()){
            throw new BusinessException(ResponseCodeEnum.CODE_600); // 参数校验不通过 既然绕过前端了就不再进行具体提示
        } // 要传的没传

        // 要校验长度 但超了或不足
        if(!isEmpty &&
                (verStuff.max() != -1 && len > verStuff.max() ||
                verStuff.min() != -1 && len < verStuff.min())
        ){
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        // 传了 长度符合了 最后看正则
        if(!isEmpty &&
                !StringTools.isEmpty(verStuff.regex().getRegex()) && // regex = VerifyRegexEnum.PHONE
                !VerifyUtils.verify(verStuff.regex(), String.valueOf(val))
        ){
            throw new BusinessException(ResponseCodeEnum.CODE_600); // 传参有值 且正则不空（选了要正则）
        }
    }

    private void checkObjVal(Parameter param, Object val){ // 利用reflect

        try {
            String typeName = param.getParameterizedType().getTypeName(); // Account
            Class clz = Class.forName(typeName); // Account info
            Field[] fields = clz.getDeclaredFields();

            for(Field f:fields){
                VerifyParam fVerStuff = f.getAnnotation(VerifyParam.class); // 对字段的校验填充
                if(fVerStuff == null) continue; // 只关系需要校验的字段

                f.setAccessible(true); // can get private
                Object resVal = f.get(val); // 获取指定对象 val 中字段 f 的值
                checkBaseVal(resVal, fVerStuff); // Po 内的都是基本属性 校验其与填充

            }

        } catch (Exception e) {

            logger.error("校验参数失败", e);
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

    }
}
