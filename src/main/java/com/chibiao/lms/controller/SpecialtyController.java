package com.chibiao.lms.controller;

import com.chibiao.lms.domain.Specialty;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.HttpResult;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.SpecialtyService;
import com.chibiao.lms.util.HttpResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 专业管理控制器
 *
 * @author : 迟彪
 * @date : 2020/3/21
 */
@RestController
@RequestMapping("/specialty")
public class SpecialtyController {
    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping("/specialtyList")
    @ResponseBody
    public PageListRes specialtyList(PageParam pageParam){
        return specialtyService.querySpecialty(pageParam);
    }

    @PostMapping("/addSpecialty")
    @ResponseBody
    public HttpResult<Boolean> addSpecialty(Specialty specialty){
        Boolean result = specialtyService.addSpecialty(specialty);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }
    /**
     * 根据院系编号获取专业list
     */
    @GetMapping("/specialtyByDeptNo/{detpNo}")
    @ResponseBody
    public HttpResult<List<Specialty>> specialtyByDeptNo(@PathVariable("detpNo") Long detpNo){
        List<Specialty> result = specialtyService.specialtyByDeptNo(detpNo);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }


}
