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
}