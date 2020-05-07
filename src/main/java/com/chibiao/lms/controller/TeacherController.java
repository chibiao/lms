package com.chibiao.lms.controller;

import com.chibiao.lms.annotation.Log;
import com.chibiao.lms.domain.*;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.HttpResult;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.ClazzCourseTimeService;
import com.chibiao.lms.service.CourseService;
import com.chibiao.lms.service.SpecialtyService;
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
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private ClazzCourseTimeService clazzCourseTimeService;

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
     *
     * @return 课程信息list
     */
    @GetMapping("/selectCourseByTeacherDept")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.TeacherController.selectCourseByTeacherDept")
    public HttpResult<List<Course>> selectCourseByTeacherDept() {
        // 获取当前登录信息
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        List<Course> result = courseService.selectCourseByDept(teacher.getDepartment().getDeptNo());
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    /**
     * 查询我教授的课程
     *
     * @return 课程信息
     */
    @GetMapping("/selectMyCourse")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.TeacherController.selectMyCourse", errorReturnHttpResult = false)
    public PageListRes selectMyCourse() {
        // 获取当前登录信息
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        List<Course> result = teacherService.selectMyCourse(teacher.getTeacherNo());
        return PageListResUtil.buildSuccessListRes(result);
    }

    /**
     * 添加教师班级信息
     *
     * @param teacherClazzRel 教师和班级关系
     * @return 是否添加成功
     */
    @PostMapping("/addMyClazz")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.TeacherController.addMyClazz")
    public HttpResult<Boolean> addMyClazz(TeacherClazzRel teacherClazzRel) {
        // 获取当前登录信息
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        teacherClazzRel.setTeacherNo(teacher.getTeacherNo());
        Boolean result = teacherService.addMyClazz(teacherClazzRel);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    /**
     * 查询我的班级信息
     *
     * @return 班级信息
     */
    @GetMapping("/selectMyClazz")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.TeacherController.selectMyClazz", errorReturnHttpResult = false)
    public PageListRes selectMyClazz() {
        // 获取当前登录信息
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        List<Clazz> clazzes = teacherService.selectMyClazz(teacher.getTeacherNo());
        return PageListResUtil.buildSuccessListRes(clazzes);
    }

    /**
     * 根据教师的编号查询出专业信息
     */
    @GetMapping("/specialtyByTeacherNo")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.SpecialtyController.specialtyByDeptNo")
    public HttpResult<List<Specialty>> specialtyByTeacherNo() {
        // 获取当前登录信息
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        List<Specialty> result = specialtyService.specialtyByDeptNo(teacher.getDepartment().getDeptNo());
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    /**
     * 添加教师的班级课程授课时间
     * @param clazzCourseTime 上课时间
     * @return 是否添加成功
     */
    @PostMapping("/addClazzCourseTime")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.TeacherController.addClazzCourseTime")
    public HttpResult<Boolean> addClazzCourseTime(ClazzCourseTime clazzCourseTime){
        // 获取当前登录信息
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        clazzCourseTime.setTeacher(teacher);
        Boolean result = clazzCourseTimeService.addClazzCourseTime(clazzCourseTime);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    /**
     * 根据教师编号查询教师的上课时间
     * @return 上课时间list
     */
    @GetMapping("/selectClazzCourseTimeByTeacherNo")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.TeacherController.selectClazzCourseTimeByTeacherNo",errorReturnHttpResult = false)
    public PageListRes selectClazzCourseTimeByTeacherNo(){
        // 获取当前登录信息
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        List<ClazzCourseTime> clazzCourseTimes = clazzCourseTimeService.selectClazzCourseTimeByTeacherNo(teacher.getTeacherNo());
        return PageListResUtil.buildSuccessListRes(clazzCourseTimes);
    }

}
