package com.chibiao.lms.mapper;

import com.chibiao.lms.domain.Course;
import java.util.List;

public interface CourseMapper {

    int insert(Course record);


    /**
     * 查询课程信息
     * @param course 查询条件
     * @return
     */
    List<Course> queryCourses(Course course);

    /**
     * 根据教师编号  查询教师教授的课程
     * @param teacherNo 教师编号
     * @return 课程信息list
     */
    List<Course> selectMyCourse(Long teacherNo);

    /**
     * 根据院系编号查询课程信息
     * @param deptNo 院系编号
     * @return
     */
    List<Course> selectCourseByDept(Long deptNo);
}