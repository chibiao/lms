package com.chibiao.lms.domain;

import lombok.Data;

/**
 * 院系
 * @author 迟彪
 */
@Data
public class Department {
    /**
     * 主键
     */
    private Long id;
    /**
     * 院系编号
     */
    private Long deptNo;
    /**
     * 院系名称
     */
    private String deptName;
    /**
     * 院系描述
     */
    private String deptDesc;

}