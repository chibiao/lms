package com.chibiao.lms.param;

import lombok.Data;

import java.util.Date;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/4/20
 */
@Data
public class EmailParam {
    private String teacherName;
    private String studentName;
    private String leaveReason;
    private Date startTime;
    private Date endTime;
    private Double leaveDays;
}
