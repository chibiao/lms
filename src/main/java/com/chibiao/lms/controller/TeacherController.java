package com.chibiao.lms.controller;

import com.chibiao.lms.domain.Teacher;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.HttpResult;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.TeacherService;
import com.chibiao.lms.util.HttpResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/teacherList")
    @ResponseBody
    public PageListRes teacherList(Teacher teacher, PageParam pageParam) {
        return teacherService.queryTeachers(teacher, pageParam);
    }

    @PostMapping("/addTeacher")
    @ResponseBody
    public HttpResult<Boolean> addTeacher(Teacher teacher) {
        Boolean result = teacherService.addTeacher(teacher);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    @DeleteMapping("/deleteTeacher/{teacherNo}")
    @ResponseBody
    public HttpResult<Boolean> deleteTeacher(@PathVariable("teacherNo") Long teacherNo) {
        Boolean result = teacherService.deleteTeacher(teacherNo);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    @PutMapping("/updateTeacher")
    @ResponseBody
    public HttpResult<Boolean> updateTeacher(Teacher teacher) {
        Boolean result = teacherService.updateTeacher(teacher);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }
}
