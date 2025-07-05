package com.easyjob.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import com.easyjob.exception.BusinessException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StringTools {

    public static boolean isEmpty(String str) {

        return str == null || str.trim().isEmpty();
    }

    public static String encodeByMD5(String pwd) {

        return isEmpty(pwd)? null: DigestUtils.md5Hex(pwd);
    }

    public static String getFileSuffix(String fileName) {

        int dotPos = fileName.lastIndexOf(".");
        if(dotPos == -1) return "";

        return fileName.substring(dotPos); // .+->
    }

    public static String getRandomString(int len) {

        return RandomStringUtils.random(len, true, true);
    }

    public static void checkParam(Object param) {
        try {
            Field[] fields = param.getClass().getDeclaredFields();
            boolean notEmpty = false;
            for (Field field : fields) {
                String methodName = "get" + StringTools.upperCaseFirstLetter(field.getName());
                Method method = param.getClass().getMethod(methodName);
                Object obj = method.invoke(param);
                if (obj != null && obj instanceof java.lang.String && !StringTools.isEmpty(obj.toString())
                        || obj != null && !(obj instanceof java.lang.String)) {
                    notEmpty = true;
                    break;
                }
            }
            if (!notEmpty) {
                throw new BusinessException("多参数更新，删除，必须有非空条件");
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("校验参数是否为空失败");
        }
    }

    public static String upperCaseFirstLetter(String field) {
        if (isEmpty(field)) {
            return field;
        }
        //如果第二个字母是大写，第一个字母不大写
        if (field.length() > 1 && Character.isUpperCase(field.charAt(1))) {
            return field;
        }
        return field.substring(0, 1).toUpperCase() + field.substring(1);
    }
}
