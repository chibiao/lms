package com.chibiao.lms.controller;

import com.chibiao.lms.domain.Clazz;
import com.chibiao.lms.exception.BusinessException;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.HttpResult;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.ClazzService;
import com.chibiao.lms.util.HttpResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级Controller
 *
 * @author : 迟彪
 * @date : 2020/4/11
 */
@RestController
@RequestMapping("/clazz")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @PostMapping("/addClazz")
    @ResponseBody
    public HttpResult<Boolean> addClazz(Clazz clazz) {
        try {
            Boolean result = clazzService.addClazz(clazz);
            return HttpResultUtil.buildSuccessHttpResult(result);
        } catch (BusinessException e) {
            return HttpResultUtil.buildHttpResult(e.getErrorCode());
        } catch (Exception e) {
            return HttpResultUtil.buildHttpFaultResult();
        }
    }

    @GetMapping("/clazzList")
    @ResponseBody
    public PageListRes clazzList(PageParam pageParam) {
        return clazzService.queryClazz(pageParam);
    }

    @GetMapping("/clazzBySpecialtyNo/{specialtyNo}")
    @ResponseBody
    public HttpResult<List<Clazz>> clazzBySpecialtyNo(@PathVariable Long specialtyNo){
        List<Clazz> result = clazzService.clazzBySpecialtyNo(specialtyNo);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }
}
