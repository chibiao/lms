package com.chibiao.lms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.util.Date;

/**
 * 班级课程上课时间
 * @date 2020/05/06
 * @author 迟彪
 */
@Data
public class ClazzCourseTime {
    /**
     * 主键
     */
    private Long id;
    /**
     * 班级编号
     */
    private Clazz clazz;
    /**
     * 课程编号
     */
    private Course course;
    /**
     * 上课时间
     */
    @JsonFormat(pattern = "HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Date courseBeginTime;
    /**
     * 下课时间
     */
    @JsonFormat(pattern = "HH:mm:ss")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Date courseEndTime;
    /**
     * 周几上课
     */
    private Integer courseWeek;
    /**
     * 课程开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date courseBeginDay;
    /**
     * 课程结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date courseEndDay;
    /**
     * 上课老师编号
     */
    private Teacher teacher;

}