package com.easyjob.utils;

import org.springframework.beans.BeanUtils;
import java.util.ArrayList;
import java.util.List;

public class CopyTools {

    /**
     * 拷贝列表指定对象 到目标类型
     * @param srcLst 源对象列表
     * @param clz      目标对象类型的Class
     * @param <T>        目标类型
     * @param <S>        源类型
     * @return 拷贝后的目标对象列表
     */
    public static <T, S> List<T> copyList(List<S> srcLst, Class<T> clz) { // 复制对象列表
        
        List<T> resLst = new ArrayList<>();

        if (srcLst == null || srcLst.isEmpty()) return resLst;

        for (S source : srcLst) {

            T target = copy(source, clz);
            if (target != null) {
                resLst.add(target);
            }
        }

        return resLst;
    }

    /**
     * 拷贝一个对象 到 指定目标类型
     * @param src 源对象
     * @param clz  目标类型的Class
     * @param <T>    目标类型
     * @param <S>    源类型
     * @return 拷贝后的目标对象
     */
    public static <T, S> T copy(S src, Class<T> clz) { // 复制单个对象

        if (src == null || clz == null) return null;

        try {
            T target = clz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(src, target); // spring copy
            return target;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}





