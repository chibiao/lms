package com.chibiao.lms.service;

import com.chibiao.lms.domain.ClazzCourseTime;

import java.util.List;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/5/6
 */
public interface ClazzCourseTimeService {
    /**
     * 添加教师的班级课程授课时间
     * @param clazzCourseTime 上课时间
     * @return 是否添加成功
     */
    Boolean addClazzCourseTime(ClazzCourseTime clazzCourseTime);

    /**
     * 根据教师编号查询教师的上课时间
     * @param teacherNo 教师编号
     * @return 上课时间list
     */
    List<ClazzCourseTime> selectClazzCourseTimeByTeacherNo(Long teacherNo);
}
