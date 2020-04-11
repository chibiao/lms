package com.chibiao.lms.domain;

import lombok.Data;

/**
 * 专业
 * @author 迟彪
 */
@Data
public class Specialty {
    /**
     * 主键
     */
    private Long id;
    /**
     * 专业编号
     */
    private Long specialtyNo;
    /**
     * 专业名称
     */
    private String specialtyName;
    /**
     * 专业描述
     */
    private String specialtyDesc;
    /**
     * 院系编号
     */
    private Department department;

}