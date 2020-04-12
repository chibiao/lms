package com.chibiao.lms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
public class Student {
    /**
     * 主键
     */
    private Long id;
    /**
     * 学生id
     */
    private Long studentId;
    /**
     * 学生姓名
     */
    private String studentName;
    /**
     * 密码
     */
    private String studentPassword;
    /**
     * 电话
     */
    private String studentPhone;
    /**
     * 邮箱
     */
    private String studentEmail;
    /**
     * 性别 0 男 1 女
     */
    private String studentSex;
    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date studentBirthday;
    /**
     * 年龄
     */
    private Integer studentAge;
    /**
     * 班级
     */
    private Clazz clazz;
    /**
     * 专业
     */
    private Specialty specialty;
    /**
     * 院系
     */
    private Department department;

}