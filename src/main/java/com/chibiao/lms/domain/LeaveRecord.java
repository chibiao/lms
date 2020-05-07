package com.chibiao.lms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 请假信息表
 * @author 迟彪
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
     * 请假原因
     */
    private String leaveReason;
    /**
     * 请假单状态 0新建 1审批中 2审批完成 3已放弃
     */
    private Integer leaveStatus;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leaveBeginTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leaveEndTime;
    /**
     * 请假天数
     */
    private Integer leaveDays;
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