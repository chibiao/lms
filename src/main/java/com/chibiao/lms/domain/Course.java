package com.chibiao.lms.domain;

import lombok.Data;

@Data
public class Course {
    /**
     * 课程编号
     */
    private Long courseNo;
    /**
     * 课程名称
     */
    private String courseName;
    /**
     * 课程描述
     */
    private String courseDesc;

    /**
     * 课程所属院系
     */
    private Department department;

}