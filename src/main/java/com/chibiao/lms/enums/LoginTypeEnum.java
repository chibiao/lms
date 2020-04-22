package com.chibiao.lms.enums;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/4/22
 */
public enum  LoginTypeEnum {
    STUDENT(1,"学生"),
    TEACHER(2,"教师"),
    ADMIN(3,"管理员"),
    ;



    private Integer type;
    private String desc;
    LoginTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public Integer getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
