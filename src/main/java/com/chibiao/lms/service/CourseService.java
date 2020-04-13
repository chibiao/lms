package com.chibiao.lms.service;

import com.chibiao.lms.domain.Course;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;

/**
 * 课程服务
 *
 * @author : 迟彪
 * @date : 2020/4/12
 */
public interface CourseService {
    /**
     * 分页查询课程信息
     * @param course 查询条件
     * @param pageParam 分页参数
     * @return
     */
    PageListRes queryCourses(Course course, PageParam pageParam);

    /**
     * 添加课程
     * @param course 课程信息
     * @return
     */
    Boolean addCourse(Course course);

}
