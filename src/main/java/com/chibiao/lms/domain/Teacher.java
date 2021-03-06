package com.chibiao.lms.domain;

import lombok.Data;

import java.util.List;

@Data
public class Teacher {
    /**
     * 主键
     */
    private Long id;
    /**
     * 教师编号
     */
    private Long teacherNo;
    /**
     * 教师名称
     */
    private String teacherName;
    /**
     * 密码
     */
    private String teacherPassword;
    /**
     * 邮箱
     */
    private String teacherEmail;
    /**
     * 电话
     */
    private String teacherPhone;
    /**
     * 教师性别
     */
    private Integer teacherSex;
    /**
     * 类型 0 教师 1 辅导员
     */
    private Integer teacherType;

    /**
     * 教师所属院系
     */
    private Department department;

    /**
     * 教师教授的课程
     */
    private List<Course> courses;
}