package com.chibiao.lms.controller;

import com.chibiao.lms.annotation.Log;
import com.chibiao.lms.domain.Admin;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.HttpResult;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.AdminService;
import com.chibiao.lms.util.HttpResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 请输入描述
 *
 * @author : 迟彪
 * @date : 2020/4/22
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/adminList")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.AdminController.adminList", errorReturnHttpResult = false)
    public PageListRes adminList(PageParam pageParam) {
        return adminService.queryAdminList(pageParam);
    }

    @PostMapping("/addAdmin")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.AdminController.addAdmin")
    public HttpResult<Boolean> addAdmin(Admin admin) {
        Boolean result = adminService.addAdmin(admin);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

}
