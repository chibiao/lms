package com.chibiao.lms.service.impl;

import com.chibiao.lms.domain.Course;
import com.chibiao.lms.mapper.CourseMapper;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.CourseService;
import com.chibiao.lms.util.PageListResUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 课程信息服务
 *
 * @author : 迟彪
 * @date : 2020/4/12
 */
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public PageListRes queryCourses(Course course, PageParam pageParam) {
        Page<Object> page = PageHelper.startPage(pageParam.getPage(), pageParam.getLimit());
        List<Course> list = courseMapper.queryCourses(course);
        return PageListResUtil.buildSuccessPageListRes(page, list);
    }

    @Override
    public Boolean addCourse(Course course) {
        courseMapper.insert(course);
        return Boolean.TRUE;
    }
}
