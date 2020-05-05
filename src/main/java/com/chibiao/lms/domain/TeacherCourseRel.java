package com.chibiao.lms.domain;

import lombok.Data;

/**
 * 教师和课程关系
 *
 * @author : 迟彪
 * @date : 2020/5/5
 */
@Data
public class TeacherCourseRel {
    /**
     * 课程编号
     */
    private Long courseNo;
    /**
     * 教师编号
     */
    private Long teacherNo;
}
