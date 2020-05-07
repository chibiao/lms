package com.chibiao.lms.controller;

import com.chibiao.lms.annotation.Log;
import com.chibiao.lms.domain.LeaveRecord;
import com.chibiao.lms.domain.Student;
import com.chibiao.lms.param.PageParam;
import com.chibiao.lms.result.HttpResult;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.LeaveRecordService;
import com.chibiao.lms.util.HttpResultUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * 请假单信息管理
 *
 * @author : 迟彪
 * @date : 2020/5/7
 */
@RestController
@RequestMapping("/leaveRecord")
public class LeaveRecordController {
    @Autowired
    private LeaveRecordService leaveRecordService;

    /**
     * 添加请假单
     *
     * @param leaveRecord 请假单信息
     * @return 是否添加成功
     */
    @PostMapping("/addLeaveRecord")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.LeaveRecordController.addLeaveRecord")
    public HttpResult<Boolean> addLeaveRecord(LeaveRecord leaveRecord){
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        leaveRecord.setStudentId(student.getStudentId());
        leaveRecord.setStudentName(student.getStudentName());
        leaveRecord.setDeptNo(student.getDepartment().getDeptNo());
        leaveRecord.setSpecialtyNo(student.getSpecialty().getSpecialtyNo());
        leaveRecord.setClazzNo(student.getClazz().getClazzNo());
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(leaveRecord.getLeaveBeginTime());
        calendar.add(calendar.DATE,leaveRecord.getLeaveDays());
        leaveRecord.setLeaveEndTime(calendar.getTime());
        Boolean result = leaveRecordService.addLeaveRecord(leaveRecord);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    /**
     * 更新请假单信息
     *
     * @param leaveRecord 请假单信息
     * @return 是否更新成功
     */
    @PutMapping("/updateLeaveRecord")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.LeaveRecordController.updateLeaveRecord")
    public HttpResult<Boolean> updateLeaveRecord(LeaveRecord leaveRecord){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(leaveRecord.getLeaveBeginTime());
        calendar.add(calendar.DATE,leaveRecord.getLeaveDays());
        leaveRecord.setLeaveEndTime(calendar.getTime());
        Boolean result = leaveRecordService.updateLeaveRecord(leaveRecord);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    /**
     * 删除请假单信息
     *
     * @param id 请假单id
     * @return 是否删除成功
     */
    @DeleteMapping("/deleteLeaveRecord/{id}")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.LeaveRecordController.deleteLeaveRecord")
    public HttpResult<Boolean> deleteLeaveRecord(@PathVariable("id") Long id){
        Boolean result = leaveRecordService.deleteLeaveRecord(id);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    /**
     * 更新请假单状态
     *
     * @param id          请假单id
     * @param leaveStatus 请假单状态
     * @return 是否更新成功
     */
    @PutMapping("/updateLeaveStatus")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.LeaveRecordController.updateLeaveStatus")
    public HttpResult<Boolean> updateLeaveStatus(Long id, Integer leaveStatus){
        Boolean result = leaveRecordService.updateLeaveStatus(id, leaveStatus);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    /**
     * 查询请假单
     *
     * @param leaveRecord 请假单入参
     * @return 请假单list
     */
    @GetMapping("/selectLeaveRecord")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.LeaveRecordController.selectLeaveRecord",errorReturnHttpResult = false)
    public PageListRes selectLeaveRecord(LeaveRecord leaveRecord, PageParam pageParam){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        // 判断是不是学生 如果是 只可以查看自己的请假单
        if (principal instanceof Student){
            leaveRecord.setStudentId(((Student) principal).getStudentId());
        }
        // 不是学生可以查看所有的请假单
        return leaveRecordService.selectLeaveRecord(leaveRecord, pageParam);
    }
}
