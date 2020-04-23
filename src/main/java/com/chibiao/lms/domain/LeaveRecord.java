package com.chibiao.lms.domain;

import lombok.Data;

import java.util.Date;

/**
 * 请假信息表
 */
@Data
public class LeaveRecord {
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
     * 请假原因
     */
    private String leaveReason;
    /**
     * 开始时间
     */
    private Date leaveBeginTime;
    /**
     * 结束时间
     */
    private Date leaveEndTime;
    /**
     * 请假天数
     */
    private Double leaveDays;
    /**
     * 专业编号
     */
    private Long specialtyNo;
    /**
     * 院系编号
     */
    private Long deptNo;
    /**
     * 班级编号
     */
    private Long clazzNo;

}