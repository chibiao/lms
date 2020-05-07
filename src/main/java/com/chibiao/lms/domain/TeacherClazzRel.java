package com.chibiao.lms.domain;

import lombok.Data;

/**
 * 教师和课程关系
 *
 * @author : 迟彪
 * @date : 2020/5/6
 */
@Data
public class TeacherClazzRel {
    /**
     * 教师编号
     */
    private Long teacherNo;
    /**
     * 课程编号
     */
    private Long clazzNo;
}
