package com.chibiao.lms.controller;

import com.chibiao.lms.annotation.Log;
import com.chibiao.lms.domain.LeaveRecord;
import com.chibiao.lms.domain.Student;
import com.chibiao.lms.domain.Teacher;
import com.chibiao.lms.domain.WorkFlowVo;
import com.chibiao.lms.error.BusinessErrorCode;
import com.chibiao.lms.result.HttpResult;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.LeaveRecordService;
import com.chibiao.lms.service.WorkFlowService;
import com.chibiao.lms.util.HttpResultUtil;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 工作流的控制器
 *
 * @author 迟彪
 */
@RestController
@RequestMapping("/workFlow")
public class WorkFlowController {

    @Autowired
    private WorkFlowService workFlowService;

    @Autowired
    private LeaveRecordService leaveRecordService;

    /**
     * 加载部署信息数据
     */
    @GetMapping("/loadAllDeployment")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.WorkFlowController.loadAllDeployment", errorReturnHttpResult = false)
    public PageListRes loadAllDeployment(WorkFlowVo workFlowVo) {
        return this.workFlowService.queryProcessDeploy(workFlowVo);
    }

    /**
     * 加载流程定义信息数据
     */
    @GetMapping("/loadAllProcessDefinition")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.WorkFlowController.loadAllProcessDefinition", errorReturnHttpResult = false)
    public PageListRes loadAllProcessDefinition(WorkFlowVo workFlowVo) {
        return this.workFlowService.queryAllProcessDefinition(workFlowVo);
    }

    @RequestMapping("/addWorkFlow")
    @ResponseBody
    public HttpResult<Boolean> addWorkFlow(MultipartFile mf, WorkFlowVo workFlowVo) {
        try {
            this.workFlowService.addWorkFlow(mf.getInputStream(), workFlowVo.getDeploymentName());
            return HttpResultUtil.buildSuccessHttpResult(Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
            return HttpResultUtil.buildHttpResult(BusinessErrorCode.UNKNOWN_ERROR);
        }
    }

    @RequestMapping("/deleteWorkFlow")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.WorkFlowController.deleteWorkFlow")
    public HttpResult<Boolean> deleteWorkFlow(WorkFlowVo workFlowVo) {
        this.workFlowService.deleteWorkFlow(workFlowVo.getDeploymentId());
        return HttpResultUtil.buildSuccessHttpResult(Boolean.TRUE);
    }

    @RequestMapping("/viewProcessImage")
    public void viewProcessImage(WorkFlowVo workFlowVo, HttpServletResponse response) {
        String deploymentId = workFlowVo.getDeploymentId();
        if (StringUtils.isEmpty(deploymentId)){
            ProcessDefinition processDefinition=this.workFlowService.queryProcessDefinitionByTaskId(workFlowVo.getTaskId());
            //取出流程部署ID
            deploymentId = processDefinition.getDeploymentId();
        }
        InputStream stream = this.workFlowService.queryProcessDeploymentImage(deploymentId);
        try {
            BufferedImage image = ImageIO.read(stream);
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "JPEG", outputStream);
            stream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/startProcess")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.WorkFlowController.startProcess")
    public HttpResult<Boolean> startProcess(WorkFlowVo workFlowVo) {
        Long leaveRecordId = workFlowVo.getId();
        this.workFlowService.startProcess(leaveRecordId);
        return HttpResultUtil.buildSuccessHttpResult(Boolean.TRUE);
    }

    @RequestMapping("/loadCurrentUserTask")
    @ResponseBody
    public PageListRes loadCurrentUserTask(WorkFlowVo workFlowVo) {
        return this.workFlowService.queryCurrentUserTask(workFlowVo);
    }

    @GetMapping("/toDoTask/{taskId}")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.WorkFlowController.toDoTask")
    public HttpResult<LeaveRecord> toDoTask(@PathVariable("taskId") String taskId) {
        //1,根据任务ID查询请假单的信息
        LeaveRecord result = this.workFlowService.queryLeaveRecordByTaskId(taskId);
        return HttpResultUtil.buildSuccessHttpResult(result);
    }

    @RequestMapping("/loadAllCommentByTaskId")
    @ResponseBody
    public PageListRes loadAllCommentByTaskId(WorkFlowVo workFlowVo) {
        return this.workFlowService.queryCommentByTaskId(workFlowVo.getTaskId());
    }

    @RequestMapping("/doTask")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.WorkFlowController.doTask")
    public HttpResult<Boolean> doTask(WorkFlowVo workFlowVo) {
        this.workFlowService.completeTask(workFlowVo);
        return HttpResultUtil.buildSuccessHttpResult(Boolean.TRUE);
    }


    @RequestMapping("/loadCommentByLeaveRecordId")
    @ResponseBody
    @Log(jKey = "com.chibiao.lms.controller.WorkFlowController.loadCommentByLeaveRecordId",errorReturnHttpResult = false)
    public PageListRes loadCommentByLeaveRecordId(WorkFlowVo workFlowVo) {
        return this.workFlowService.querydCommentByLeaveRecordId(workFlowVo.getId());
    }

    @GetMapping("/queryHistoryTask")
    @ResponseBody
    public PageListRes queryHistoryTask(WorkFlowVo workFlowVo){
        Object principal = SecurityUtils.getSubject().getPrincipal();
        String assignee = null;
        if (principal instanceof Teacher){
            assignee = ((Teacher) principal).getTeacherName();
        }
        if (principal instanceof Student){
            assignee = ((Student) principal).getStudentName();
        }
        return this.workFlowService.queryHistoryTask(workFlowVo,assignee);
    }

}
