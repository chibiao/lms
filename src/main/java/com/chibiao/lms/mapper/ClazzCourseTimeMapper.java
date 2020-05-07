package com.chibiao.lms.mapper;

import com.chibiao.lms.domain.ClazzCourseTime;
import java.util.List;

public interface ClazzCourseTimeMapper {
    int addClazzCourseTime(ClazzCourseTime clazzCourseTime);

    /**
     * 根据教师编号查询教师的上课时间
     * @param teacherNo 教师编号
     * @return 上课时间list
     */
    List<ClazzCourseTime> selectClazzCourseTimeByTeacherNo(Long teacherNo);

    /**
     * 查询上课时间是否冲突
     * @param clazzCourseTime
     * @return
     */
    ClazzCourseTime selectClazzCourseTime(ClazzCourseTime clazzCourseTime);

    /**
     * 根据课程编号查询班级上课时间
     * @param clazzCourseTime 班级上课时间
     * @return 班级上课时间list
     */
    List<ClazzCourseTime> selectClazzCourseTimeByClazzNo(ClazzCourseTime clazzCourseTime);
}