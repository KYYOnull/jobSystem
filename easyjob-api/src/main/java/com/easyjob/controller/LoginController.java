package com.easyjob.controller;

import com.easyjob.entity.constants.Constants;
import com.easyjob.entity.dto.CreateImageCode;
import com.easyjob.service.AppUserInfoService;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginController extends BaseController{

    @Resource
    private AppUserInfoService userInfoSvc;

    @RequestMapping("/checkCode")
    public void checkCode(HttpServletResponse res, String deviceIdByUniApp, Integer checkCodeType) throws IOException {

        // 在session的场景下 用户标识即cookie id
        // 不使用session 则必须手动返一个唯一标识
        // 图片转为 base64字符串  服务端存为 redis.set(uuid, codeStr)
        // login 时 redis.get(inId) 与接口传入的 checkCodeIn 比对
        CreateImageCode vCode = new CreateImageCode(130, 38, 5, 10); // 创建图片验证码

        res.setHeader("Pragma", "no-cache"); // 设置响应头，禁止缓存
        res.setHeader("Cache-Control", "no-cache");
        res.setDateHeader("Expires", 0);
        res.setContentType("image/jpeg"); // 设置响应内容类型为图片

        String code = vCode.getCode(); // 获取生成的验证码
//        ses.setAttribute(Constants.CHECK_CODE_KEY, code); // 将验证码存入 Session
        vCode.write(res.getOutputStream()); // 将验证码图片写入响应流
    }
}
