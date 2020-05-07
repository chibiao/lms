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
     * 请假单标题
     */
    private String leaveTitle;
    /**
     * 请假单状态 请假单状态 1,新建  2，已提交 3，审批中 4，审批完成
     */
    private Integer leaveStatus;
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