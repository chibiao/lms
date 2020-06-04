package com.chibiao.lms.controller;

import com.chibiao.lms.annotation.Log;
import com.chibiao.lms.authc.UserNamePasswordLoginTypeToken;
import com.chibiao.lms.domain.Student;
import com.chibiao.lms.error.BusinessErrorCode;
import com.chibiao.lms.result.HttpResult;
import com.chibiao.lms.service.StudentService;
import com.chibiao.lms.util.HttpResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/4/22
 */
@Controller
@Slf4j
public class LoginController {
    @Autowired
    private StudentService studentService;
    @GetMapping("/login")
    public String defaultLogin(){
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.LoginController.login",errorReturnHttpResult = false)
    public HttpResult<Boolean> login(String username, String password, Integer loginType){
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UserNamePasswordLoginTypeToken token = new UserNamePasswordLoginTypeToken(username, password,loginType);
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            log.error("LoginController -> login exception, username={}, password={}, loginType={}", username, password, loginType, uae);
            return HttpResultUtil.buildHttpResult(BusinessErrorCode.UNKOWN_ACCOUNT);
        } catch (IncorrectCredentialsException ice) {
            log.error("LoginController -> login exception, username={}, password={}, loginType={}", username, password, loginType, ice);
            return HttpResultUtil.buildHttpResult(BusinessErrorCode.PASSWORD_FALSE);
        } catch (AuthenticationException ae) {
            log.error("LoginController -> login exception, username={}, password={}, loginType={}", username, password, loginType, ae);
            return HttpResultUtil.buildHttpResult(BusinessErrorCode.ACCOUNT_OR_PASSWORD_FALSE);
        }
        return HttpResultUtil.buildSuccessHttpResult(Boolean.TRUE);
    }

    @PostMapping("/updatePassword")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.StudentController.updateStudentPassword")
    public HttpResult<Boolean> updateStudentPassword(String oldPassword,String newPassword){
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        Boolean result = studentService.updateStudentPassword(student,oldPassword,newPassword);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }
}
