package com.chibiao.lms.domain;

import lombok.Data;

@Data
public class Clazz {
    /**
     * 主键
     */
    private Long id;
    /**
     * 班级编号
     */
    private Long clazzNo;
    /**
     * 班级年份
     */
    private Integer clazzYear;
    /**
     * 班级名称
     */
    private String clazzName;
    /**
     * 班级描述
     */
    private String clazzDesc;
    /**
     * 所属专业
     */
    private Specialty specialty;
    /**
     * 所属院系
     */
    private Department department;
}