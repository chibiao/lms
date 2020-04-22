package com.chibiao.lms.domain;

import lombok.Data;

/**
 * 管理员信息表
 * @author 迟彪
 */
@Data
public class Admin {
    /**
     * 主键
     */
    private Long id;
    /**
     * 账号
     */
    private String adminAccount;
    /**
     * 密码
     */
    private String adminPassword;
}