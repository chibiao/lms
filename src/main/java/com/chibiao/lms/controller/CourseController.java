package com.chibiao.lms.controller;

import com.chibiao.lms.domain.Course;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.HttpResult;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.CourseService;
import com.chibiao.lms.util.HttpResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 课程管理Controller
 *
 * @author : 迟彪
 * @date : 2020/4/12
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/queryCourses")
    @ResponseBody
    public PageListRes queryCourses(Course course, PageParam pageParam) {
        return courseService.queryCourses(course, pageParam);
    }

    @PostMapping("/addCourse")
    @ResponseBody
    public HttpResult<Boolean> addCourse(Course course) {
        Boolean result = courseService.addCourse(course);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }
}
