package com.chibiao.lms.controller;

import com.chibiao.lms.annotation.Log;
import com.chibiao.lms.domain.Course;
import com.chibiao.lms.domain.Teacher;
import com.chibiao.lms.domain.TeacherCourseRel;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.HttpResult;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.CourseService;
import com.chibiao.lms.service.TeacherService;
import com.chibiao.lms.util.HttpResultUtil;
import com.chibiao.lms.util.PageListResUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 教师Controller
 *
 * @author : 迟彪
 * @date : 2020/4/12
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/teacherList")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.TeacherController.teacherList", errorReturnHttpResult = false)
    public PageListRes teacherList(Teacher teacher, PageParam pageParam) {
        return teacherService.queryTeachers(teacher, pageParam);
    }

    @PostMapping("/addTeacher")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.TeacherController.addTeacher")
    public HttpResult<Boolean> addTeacher(Teacher teacher) {
        Boolean result = teacherService.addTeacher(teacher);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    @DeleteMapping("/deleteTeacher/{teacherNo}")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.TeacherController.deleteTeacher")
    public HttpResult<Boolean> deleteTeacher(@PathVariable("teacherNo") Long teacherNo) {
        Boolean result = teacherService.deleteTeacher(teacherNo);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    @PutMapping("/updateTeacher")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.TeacherController.updateTeacher")
    public HttpResult<Boolean> updateTeacher(Teacher teacher) {
        Boolean result = teacherService.updateTeacher(teacher);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    /**
     * 添加我教授的课程
     *
     * @param teacherCourseRel 老师和课程关系
     * @return
     */
    @PostMapping("/addMyCourse")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.TeacherController.addMyCourse")
    public HttpResult<Boolean> addMyCourse(TeacherCourseRel teacherCourseRel) {
        // 获取当前登录信息
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        // 设置当前教师编号
        teacherCourseRel.setTeacherNo(teacher.getTeacherNo());
        Boolean result = teacherService.addMyCourse(teacherCourseRel);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    /**
     * 根据院系编号查询课程
     * @return 课程信息list
     */
    @GetMapping("/selectCourseByTeacherDept")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.TeacherController.selectCourseByTeacherDept")
    public HttpResult<List<Course>> selectCourseByTeacherDept(){
        // 获取当前登录信息
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        List<Course> result = courseService.selectCourseByDept(teacher.getDepartment().getDeptNo());
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    /**
     * 查询我教授的课程
     * @return 课程信息
     */
    @GetMapping("/selectMyCourse")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.TeacherController.selectMyCourse")
    public PageListRes selectMyCourse(){
        // 获取当前登录信息
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        List<Course> result = teacherService.selectMyCourse(teacher.getTeacherNo());
        return PageListResUtil.buildSuccessListRes(result);
    }
}
