package com.easyjob.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.easyjob.enums.ResponseCodeEnum;
import com.easyjob.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JsonUtils {

    private static final Logger logger= LoggerFactory.getLogger(JsonUtils.class);

    public static String obj2Json(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T json2Obj(String json, Class<T> clz) {

        try {
            return JSONObject.parseObject(json, clz);
        }catch (Exception e){
            logger.error("convert Json to Object error",e);
            throw new BusinessException(ResponseCodeEnum.CODE_601);
        }
    }

    public static <T> List<T> jsonArr2ObjList(String json, Class<T> clz) {

        try {
            return JSONArray.parseArray(json, clz);
        }catch (Exception e){
            logger.error("convert json array to List error",e);
            throw new BusinessException(ResponseCodeEnum.CODE_601);
        }
    }
}
