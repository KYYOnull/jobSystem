package com.easyjob.controller;

import com.easyjob.entity.constants.Constants;
import com.easyjob.entity.dto.SessionUserAdminDto;
import com.easyjob.enums.ResponseCodeEnum;
import com.easyjob.entity.vo.ResponseVo;

import javax.servlet.http.HttpSession;

public class BaseController {

    protected static final String STATUS_SUCCESS = "success";
    protected static final String STATUS_ERROR = "error";

    protected static <T> ResponseVo<T> getSuccessResponseVo(T t) {

        ResponseVo<T> vo = new ResponseVo<>();
        vo.setStatus(STATUS_SUCCESS);
        vo.setCode(ResponseCodeEnum.CODE_200.getCode());
        vo.setInfo(ResponseCodeEnum.CODE_200.getMsg());
        vo.setData(t);

        return vo;
    }

    // 获取session 存储的用户信息
    protected SessionUserAdminDto getUserAdminFromSession(HttpSession ses) {

        // 当前 HTTP 会话对象，由 Spring MVC 自动注入
        return (SessionUserAdminDto)ses.getAttribute(Constants.SESSION_KEY);
    }

}
