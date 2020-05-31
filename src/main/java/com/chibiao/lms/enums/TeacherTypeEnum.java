package com.chibiao.lms.enums;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/4/22
 */
public enum TeacherTypeEnum {
    TEACHER(0,"教师"),

    INSTRUCTOR(1,"辅导员")
    ;



    private Integer type;
    private String desc;
    TeacherTypeEnum(Integer type, String desc) {
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
