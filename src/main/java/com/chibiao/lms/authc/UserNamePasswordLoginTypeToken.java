package com.chibiao.lms.authc;

import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;

/**
 * 用户名/密码/登录方式Token
 *
 * @author : 迟彪
 * @date : 2020/4/22
 */
public class UserNamePasswordLoginTypeToken extends UsernamePasswordToken implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 登录方式 1 学生 2 教师 3 管理员
     */
    private Integer loginType;

    public UserNamePasswordLoginTypeToken(Integer loginType) {
        this.loginType = loginType;
    }

    public UserNamePasswordLoginTypeToken(String username, char[] password, Integer loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public UserNamePasswordLoginTypeToken(String username, String password, Integer loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public UserNamePasswordLoginTypeToken(String username, char[] password, String host, Integer loginType) {
        super(username, password, host);
        this.loginType = loginType;
    }

    public UserNamePasswordLoginTypeToken(String username, String password, String host, Integer loginType) {
        super(username, password, host);
        this.loginType = loginType;
    }

    public UserNamePasswordLoginTypeToken(String username, char[] password, boolean rememberMe, Integer loginType) {
        super(username, password, rememberMe);
        this.loginType = loginType;
    }

    public UserNamePasswordLoginTypeToken(String username, String password, boolean rememberMe, Integer loginType) {
        super(username, password, rememberMe);
        this.loginType = loginType;
    }

    public UserNamePasswordLoginTypeToken(String username, char[] password, boolean rememberMe, String host, Integer loginType) {
        super(username, password, rememberMe, host);
        this.loginType = loginType;
    }

    public UserNamePasswordLoginTypeToken(String username, String password, boolean rememberMe, String host, Integer loginType) {
        super(username, password, rememberMe, host);
        this.loginType = loginType;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }


}
