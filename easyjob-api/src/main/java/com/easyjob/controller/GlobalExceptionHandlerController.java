package com.easyjob.controller;

import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.enums.ResponseCodeEnum;
import com.easyjob.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

// 所有向上抛的异常 不捕获的话 在此全部捕获

@RestControllerAdvice
public class GlobalExceptionHandlerController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);

    @ExceptionHandler(value = Exception.class)
    public Object handleException(Exception e, HttpServletRequest request) {

        logger.error("请求错误，请求地址: {}，错误信息: {}", request.getRequestURL(), e.getMessage());

        ResponseVo<Object> ajaxResponse = new ResponseVo<>();

        if (e instanceof NoHandlerFoundException) { // 404

            ajaxResponse.setCode(ResponseCodeEnum.CODE_404.getCode());
            ajaxResponse.setInfo(ResponseCodeEnum.CODE_404.getMsg());
            ajaxResponse.setStatus(STATUS_ERROR);

        } else if (e instanceof BusinessException) { // 自定义 业务错误 BusinessException

            BusinessException biz = (BusinessException) e;
            ajaxResponse.setCode(biz.getCode());
            ajaxResponse.setInfo(biz.getMessage());
            ajaxResponse.setStatus(STATUS_ERROR);

        } else if (e instanceof BindException) { // 参数类型错误

            ajaxResponse.setCode(ResponseCodeEnum.CODE_600.getCode());
            ajaxResponse.setInfo(ResponseCodeEnum.CODE_600.getMsg());
            ajaxResponse.setStatus(STATUS_ERROR);

        } else if (e instanceof DuplicateKeyException) { // 主键冲突

            ajaxResponse.setCode(ResponseCodeEnum.CODE_601.getCode());
            ajaxResponse.setInfo(ResponseCodeEnum.CODE_601.getMsg());
            ajaxResponse.setStatus(STATUS_ERROR);

        } else { // 服务器内部错误

            ajaxResponse.setCode(ResponseCodeEnum.CODE_500.getCode());
            ajaxResponse.setInfo(ResponseCodeEnum.CODE_500.getMsg());
            ajaxResponse.setStatus(STATUS_ERROR);

        }

        return ajaxResponse; // to frontend
    }
}
