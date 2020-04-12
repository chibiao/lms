package com.chibiao.lms.controller;

import com.chibiao.lms.domain.Student;
import com.chibiao.lms.exception.BusinessException;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.HttpResult;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.StudentService;
import com.chibiao.lms.util.HttpResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 学生信息管理Controller
 *
 * @author : 迟彪
 * @date : 2020/4/11
 */
@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/studentList")
    @ResponseBody
    public PageListRes studentList(Student student, PageParam pageParam) {
        return studentService.queryStudents(student, pageParam);
    }
    @PostMapping("/addStudent")
    @ResponseBody
    public HttpResult<Boolean> addStudent(Student student){
        try {
            Boolean result = studentService.addStudent(student);
            return HttpResultUtil.buildSuccessHttpResult(result);
        }catch (BusinessException e){
            return HttpResultUtil.buildHttpResult(e.getErrorCode());
        }catch (Exception e){
            return HttpResultUtil.buildHttpFaultResult();
        }
    }

    @DeleteMapping("/deleteStudent/{studentId}")
    @ResponseBody
    public HttpResult<Boolean> deleteStudent(@PathVariable("studentId") Long studentId){
        Boolean result = studentService.deleteStudent(studentId);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    @PutMapping("/updateStudent")
    @ResponseBody
    public HttpResult<Boolean> updateStudent(Student student){
        Boolean result = studentService.updateStudent(student);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    @PutMapping("/resetStudentPassword/{studentId}")
    @ResponseBody
    public HttpResult<Boolean> resetStudentPassword(@PathVariable("studentId") Long studentId){
        Boolean result = studentService.resetStudentPassword(studentId);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }
}
