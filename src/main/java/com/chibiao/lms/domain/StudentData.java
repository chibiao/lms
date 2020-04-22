package com.chibiao.lms.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 学生数据下载模版对象
 * @author 迟彪
 */
@Data
@ColumnWidth(30)
public class StudentData {
    /**
     * 学生id
     */
    @ExcelProperty("学号")
    private Long studentId;
    /**
     * 学生姓名
     */
    @ExcelProperty("姓名")
    private String studentName;
    /**
     * 电话
     */
    @ExcelProperty("电话")
    private String studentPhone;
    /**
     * 邮箱
     */
    @ExcelProperty("邮箱")
    private String studentEmail;
    /**
     * 性别 0 男 1 女
     */
    @ExcelProperty("性别(0男 1女)")
    private Integer studentSex;
    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty(value = "生日",format = "yyyy-MM-dd")
    private Date studentBirthday;
    /**
     * 年龄
     */
    @ExcelProperty("年龄")
    private Integer studentAge;

    /**
     * 班级编号
     */
    @ExcelProperty("班级编号")
    private Long clazzNo;
    /**
     * 班级名称
     */
    @ExcelProperty("班级名称")
    private String clazzName;
    /**
     * 专业编号
     */
    @ExcelProperty("专业编号")
    private Long specialtyNo;
    /**
     * 专业名称
     */
    @ExcelProperty("专业名称")
    private String specialtyName;
    /**
     * 院系编号
     */
    @ExcelProperty("院系编号")
    private Long deptNo;
    /**
     * 院系名称
     */
    @ExcelProperty("院系名称")
    private String deptName;

}