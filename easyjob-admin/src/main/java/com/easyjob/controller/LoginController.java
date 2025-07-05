package com.easyjob.controller;

import com.easyjob.annotation.GlobalInterceptor;
import com.easyjob.annotation.VerifyParam;
import com.easyjob.entity.constants.Constants;
import com.easyjob.entity.dto.CreateImageCode;
import com.easyjob.entity.dto.SessionUserAdminDto;
import com.easyjob.entity.po.Account;
import com.easyjob.entity.vo.ResponseVo;
import com.easyjob.enums.VerifyRegexEnum;
import com.easyjob.exception.BusinessException;
import com.easyjob.service.impl.AccountServiceImpl;
import com.easyjob.utils.StringTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class LoginController extends BaseController{

    @Resource
    private AccountController accCtr;

    @Autowired
    private AccountServiceImpl accSvc;

    @RequestMapping("/checkCode")
    public void checkCode(HttpServletResponse res, HttpSession ses) throws IOException {

        CreateImageCode vCode = new CreateImageCode(130, 38, 5, 10); // 创建图片验证码

        res.setHeader("Pragma", "no-cache"); // 设置响应头，禁止缓存
        res.setHeader("Cache-Control", "no-cache");
        res.setDateHeader("Expires", 0);
        res.setContentType("image/jpeg"); // 设置响应内容类型为图片

        String code = vCode.getCode(); // 获取生成的验证码
        ses.setAttribute(Constants.CHECK_CODE_KEY, code); // 将验证码存入 Session
        vCode.write(res.getOutputStream()); // 将验证码图片写入响应流
    }

    @RequestMapping("/login")
    @GlobalInterceptor(checkParams = true) // 注解是切点 在此 可以利用反射拿到下方方法的参数
    public ResponseVo<SessionUserAdminDto> login(
            HttpSession ses,
            @VerifyParam(regex = VerifyRegexEnum.PHONE, required = true) String phone,
            @VerifyParam(required = true) String password,
            @VerifyParam(required = true) String checkCode
    ) {

        if(!checkCode.equalsIgnoreCase((String) ses.getAttribute(Constants.CHECK_CODE_KEY))) throw new BusinessException("图片验证码错误");

        SessionUserAdminDto userAdminDto = accSvc.login(phone, password); // 登录并设置
        ses.setAttribute(Constants.SESSION_KEY, userAdminDto);
        // 维护session 登录状态 权限 后续权限等信息从session中取

        return getSuccessResponseVo(userAdminDto);

    }

    @RequestMapping("/logout")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Void>  logout(HttpSession ses) {

        ses.invalidate();
        return getSuccessResponseVo(null); // father
    }

    @RequestMapping("/updateMyPwd")
    @GlobalInterceptor
    public ResponseVo<Void>  updateMyPwd(
            HttpSession ses,
            @VerifyParam(required = true, regex = VerifyRegexEnum.PASSWORD)String pwd) {

        // 获取当前登录管理员
        SessionUserAdminDto userAdminDto = getUserAdminFromSession(ses);

        Account neoAcc = new Account();
        neoAcc.setPassword(StringTools.encodeByMD5(pwd));
        accSvc.updateAccountByUserId(neoAcc, userAdminDto.getUserId());

        return getSuccessResponseVo(null);
    }

}
