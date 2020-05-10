package com.chibiao.lms.service;

import com.chibiao.lms.domain.LeaveRecord;
import com.chibiao.lms.domain.WorkFlowVo;
import com.chibiao.lms.result.PageListRes;
import org.activiti.engine.repository.ProcessDefinition;

import java.io.InputStream;
import java.util.List;

public interface WorkFlowService {
	//查询流程部署信息
    PageListRes queryProcessDeploy(WorkFlowVo workFlowVo);
	//查询所有的流程定义
    PageListRes queryAllProcessDefinition(WorkFlowVo workFlowVo);
	//添加流程部署
    void addWorkFlow(InputStream inputStream, String deploymentName);
	//根据流程部署ID删除流程部署信息
    void deleteWorkFlow(String deploymentId);
	//根据流程部署ID查询流程图
    InputStream queryProcessDeploymentImage(String deploymentId);
	//启动流程
    void startProcess(Long leaveRecordId);
	//查询当前登陆用户的待办任务
    PageListRes queryCurrentUserTask(WorkFlowVo workFlowVo);
	//根据任务ID查询请假单信息
    LeaveRecord queryLeaveRecordByTaskId(String taskId);
	//根据任务ID查询连线信息
    //List<String> queryOutComeByTaskId(String taskId);
	//根据任务ID查询批注信息
    PageListRes queryCommentByTaskId(String taskId);
	//完成任务
    void completeTask(WorkFlowVo workFlowVo);
	//根据任务ID查询流程定义对象
    ProcessDefinition queryProcessDefinitionByTaskId(String taskId);
	//根据任务ID查询任务节点坐标
    //Map<String, Object> queryTaskCoordinateByTaskId(String taskId);
	//根据请假单的ID查询批注信息
    PageListRes querydCommentByLeaveRecordId(Long id);

    List<String> queryOutComeByTaskId(String taskId);
}
