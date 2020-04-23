package com.chibiao.lms.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.chibiao.lms.annotation.Log;
import com.chibiao.lms.domain.Clazz;
import com.chibiao.lms.domain.Student;
import com.chibiao.lms.domain.StudentData;
import com.chibiao.lms.exception.BusinessException;
import com.chibiao.lms.listener.UploadStudentDataListener;
import com.chibiao.lms.param.EmailParam;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.HttpResult;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.ClazzService;
import com.chibiao.lms.service.DepartmentService;
import com.chibiao.lms.service.SpecialtyService;
import com.chibiao.lms.service.StudentService;
import com.chibiao.lms.util.HttpResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private DepartmentService departmentService;
    @GetMapping("/studentList")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.StudentController.studentList",errorReturnHttpResult = false)
    public PageListRes studentList(Student student, PageParam pageParam) {
        return studentService.queryStudents(student, pageParam);
    }

    @PostMapping("/addStudent")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.StudentController.addStudent")
    public HttpResult<Boolean> addStudent(Student student) {
        try {
            Boolean result = studentService.addStudent(student);
            return HttpResultUtil.buildSuccessHttpResult(result);
        } catch (BusinessException e) {
            return HttpResultUtil.buildHttpResult(e.getErrorCode());
        } catch (Exception e) {
            return HttpResultUtil.buildHttpFaultResult();
        }
    }

    @DeleteMapping("/deleteStudent/{studentId}")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.StudentController.deleteStudent")
    public HttpResult<Boolean> deleteStudent(@PathVariable("studentId") Long studentId) {
        Boolean result = studentService.deleteStudent(studentId);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    @PutMapping("/updateStudent")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.StudentController.updateStudent")
    public HttpResult<Boolean> updateStudent(Student student) {
        Boolean result = studentService.updateStudent(student);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    @PutMapping("/resetStudentPassword/{studentId}")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.StudentController.resetStudentPassword")
    public HttpResult<Boolean> resetStudentPassword(@PathVariable("studentId") Long studentId) {
        Boolean result = studentService.resetStudentPassword(studentId);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    /**
     * 下载学生信息导入模版
     *
     * @param request
     * @param response
     */
    @GetMapping("/downloadExcelTml")
    @ResponseBody
    public void downloadExcelTml(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long deptNo = Long.parseLong(request.getParameter("deptNo"));
        Long specialtyNo = Long.parseLong(request.getParameter("specialtyNo"));
        Long clazzNo = Long.parseLong(request.getParameter("clazzNo"));
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = null;
        try {
            fileName = URLEncoder.encode("学生导入模版", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), StudentData.class).sheet("模板").doWrite(studentTemplateData(deptNo,specialtyNo,clazzNo));
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<>(16);
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

    /**
     * 批量上传学生信息
     * @param file 文件
     * @return 是否上传成功
     * @throws IOException io异常
     */
    @PostMapping("/uploadExcelTml")
    @ResponseBody
    public HttpResult<Boolean> uploadExcelTml(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), StudentData.class, new UploadStudentDataListener(studentService)).sheet().doRead();
        return HttpResultUtil.buildSuccessHttpResult(Boolean.TRUE);
    }

    @PostMapping
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.StudentController.sendReminderMail")
    public HttpResult<Boolean> sendReminderMail(Long leaveRecordId){
        Boolean result = studentService.sendReminderMail(leaveRecordId);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    private List studentTemplateData(Long deptNo, Long specialtyNo, Long clazzNo) throws ParseException {
        List<StudentData> list = new ArrayList<>();
        StudentData studentData = new StudentData();
        studentData.setStudentId(4164001123L);
        studentData.setStudentName("张三");
        studentData.setStudentAge(18);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        studentData.setStudentBirthday(sdf.parse("1997-11-23"));
        studentData.setStudentEmail("123456@qq.com");
        studentData.setStudentPhone("1591314654651");
        Clazz clazz = clazzService.selectByClazzNo(clazzNo);
        studentData.setDeptNo(clazz.getDepartment().getDeptNo());
        studentData.setDeptName(clazz.getDepartment().getDeptName());
        studentData.setSpecialtyNo(clazz.getSpecialty().getSpecialtyNo());
        studentData.setSpecialtyName(clazz.getSpecialty().getSpecialtyName());
        studentData.setClazzNo(clazz.getClazzNo());
        studentData.setClazzName(clazz.getClazzName());
        studentData.setStudentSex(0);
        list.add(studentData);
        return list;
    }
}