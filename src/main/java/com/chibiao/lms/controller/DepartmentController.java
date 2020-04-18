package com.chibiao.lms.controller;

import com.chibiao.lms.annotation.Log;
import com.chibiao.lms.domain.Department;
import com.chibiao.lms.exception.BusinessException;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.HttpResult;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.DepartmentService;
import com.chibiao.lms.util.HttpResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 院系管理控制器
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
@RestController
@RequestMapping("/department")
@Slf4j
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/departmentList")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.DepartmentController.departmentList",errorReturnHttpResult = false)
    public PageListRes departmentList(PageParam pageParam){
        PageListRes pageListRes = departmentService.queryDepartment(pageParam);
        return pageListRes;
    }

    @PostMapping("/addDepartment")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.DepartmentController.addDepartment")
    public HttpResult<Boolean> addDepartment(Department department){
        log.info("添加院系");
        try {
            Boolean result = departmentService.addDepartment(department);
            return HttpResultUtil.buildSuccessHttpResult(result);
        }catch (BusinessException e){
            log.error(e.getMessage());
            return HttpResultUtil.buildHttpResult(e.getErrorCode());
        }catch (Exception e){
            log.error(e.getMessage());
            return HttpResultUtil.buildHttpFaultResult();
        }
    }

    @DeleteMapping("/deleteDepartment/{deptNo}")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.DepartmentController.deleteDepartment")
    public HttpResult<Boolean> deleteDepartment(@PathVariable("deptNo") Long deptNo){
        Boolean result = departmentService.deleteDepartment(deptNo);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    @GetMapping("/allDepartment")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.DepartmentController.allDepartment")
    public HttpResult<List<Department>> allDepartment(){
        List<Department> result = departmentService.allDepartment();
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

}
