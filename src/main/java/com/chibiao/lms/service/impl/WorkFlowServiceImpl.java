package com.chibiao.lms.service.impl;

import com.chibiao.lms.constant.LeaveStatusConst;
import com.chibiao.lms.domain.*;
import com.chibiao.lms.mapper.LeaveRecordMapper;
import com.chibiao.lms.result.PageListRes;
import com.chibiao.lms.service.WorkFlowService;
import com.chibiao.lms.util.PageListResUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.zip.ZipInputStream;

@Service
public class WorkFlowServiceImpl implements WorkFlowService {

	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ManagementService managementService;
	@Autowired
	private LeaveRecordMapper leaveRecordMapper;

	/**
	 * 查询流程部署信息
	 */
	public PageListRes queryProcessDeploy(WorkFlowVo workFlowVo) {
		if (workFlowVo.getDeploymentName() == null) {
			workFlowVo.setDeploymentName("");
		}
		String name = workFlowVo.getDeploymentName();
		// 查询总条数
        long count = repositoryService.createDeploymentQuery().deploymentNameLike("%" + name + "%").count();
        // 查询
		int firstResult = (workFlowVo.getPage() - 1) * workFlowVo.getLimit();
		int maxResults = workFlowVo.getLimit();
		List<Deployment> list = repositoryService.createDeploymentQuery().deploymentNameLike("%" + name + "%")
				.listPage(firstResult, maxResults);
		List<ActDeploymentEntity> data = new ArrayList<>();
		for (Deployment deployment : list) {
			ActDeploymentEntity entity = new ActDeploymentEntity();
			// copy
			BeanUtils.copyProperties(deployment, entity);
			data.add(entity);
		}
		return PageListResUtil.buildSuccessCountListRes(count,data);
	}

	/**
	 * 查询流程定义
	 */
	@Override
	public PageListRes queryAllProcessDefinition(WorkFlowVo workFlowVo) {
		if (workFlowVo.getDeploymentName() == null) {
			workFlowVo.setDeploymentName("");
		}
		String name = workFlowVo.getDeploymentName();
		// 先根据部署的的名称模糊查询出所有的部署的ID
		List<Deployment> dlist = repositoryService.createDeploymentQuery().deploymentNameLike("%" + name + "%").list();
		Set<String> deploymentIds = new HashSet<>();
		for (Deployment deployment : dlist) {
			deploymentIds.add(deployment.getId());
		}
		long count = 0;
		List<ActProcessDefinitionEntity> data = new ArrayList<>();
        Page<Object> page = new Page<>();
		if (deploymentIds.size() > 0) {
            count = this.repositoryService.createProcessDefinitionQuery()
                    .deploymentIds(deploymentIds).count();
			// 查询流程部署信息
			int firstResult = (workFlowVo.getPage() - 1) * workFlowVo.getLimit();
			int maxResults = workFlowVo.getLimit();
			List<ProcessDefinition> list = this.repositoryService.createProcessDefinitionQuery()
					.deploymentIds(deploymentIds).listPage(firstResult, maxResults);
			for (ProcessDefinition pd : list) {
				ActProcessDefinitionEntity entity = new ActProcessDefinitionEntity();
				BeanUtils.copyProperties(pd, entity);
				data.add(entity);
			}
		}
		return PageListResUtil.buildSuccessCountListRes(count,data);
	}

	// 部署流程
	@Override
	public void addWorkFlow(InputStream inputStream, String deploymentName) {
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		this.repositoryService.createDeployment().name(deploymentName).addZipInputStream(zipInputStream).deploy();
		try {
			zipInputStream.close();
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteWorkFlow(String deploymentId) {
		this.repositoryService.deleteDeployment(deploymentId, true);
	}

	/**
	 * 根据流程部署ID查询流程图
	 */
	@Override
	public InputStream queryProcessDeploymentImage(String deploymentId) {
		// 1,根据部署ID查询流程定义对象
		ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery()
				.deploymentId(deploymentId).singleResult();
		// 2从流程定义对象里面得到图片的名称
		String resourceName = processDefinition.getDiagramResourceName();
		// 3使用部署ID和图片名称去查询图片流
		InputStream stream = this.repositoryService.getResourceAsStream(deploymentId, resourceName);
		return stream;
	}

	@Override
	public void startProcess(Long leaveRecordId) {
		// 找到流程的key
		String processDefinitionKey = LeaveRecord.class.getSimpleName();
		String businessKey = processDefinitionKey + ":" + leaveRecordId;// leaveRecordId:1
		Map<String, Object> variables = new HashMap<>();
		// 设置流程变量去设置下个任务的办理人
        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
        variables.put("username", student.getStudentName());
		this.runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
		// 更新请假单的状态
        LeaveRecord record = leaveRecordMapper.selectByPrimaryKey(leaveRecordId);
        record.setLeaveStatus(LeaveStatusConst.STATE_LEAVEBILL_ONE);// 设置状态为审批中
		this.leaveRecordMapper.updateLeaveStatus(record.getId(),record.getLeaveStatus());
	}

	/**
	 * 查询当前用户的待办任务
	 */
	@Override
	public PageListRes queryCurrentUserTask(WorkFlowVo workFlowVo) {
		// 1,得到办理人信息
        Object principal = SecurityUtils.getSubject().getPrincipal();
        String assignee = null;
        if (principal instanceof Teacher){
            assignee = ((Teacher) principal).getTeacherName();
        }
        if (principal instanceof Student){
            assignee = ((Student) principal).getStudentName();
        }
        // 2,查询总数
        Page<Object> page = PageHelper.startPage(workFlowVo.getPage(), workFlowVo.getLimit());
		// 3,查询集合
		int firstResult = (workFlowVo.getPage() - 1) * workFlowVo.getLimit();
		int maxResults = workFlowVo.getLimit();
		List<Task> list = this.taskService.createTaskQuery().taskAssignee(assignee).listPage(firstResult, maxResults);
		List<ActTaskEntity> taskEntities = new ArrayList<>();
		for (Task task : list) {
			ActTaskEntity entity = new ActTaskEntity();
			BeanUtils.copyProperties(task, entity);
			taskEntities.add(entity);
		}
		return PageListResUtil.buildSuccessPageListRes(page,taskEntities);
	}

	@Override
	public LeaveRecord queryLeaveRecordByTaskId(String taskId) {
		// 1,根据任务ID查询任务实例
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		// 2,从任务里面取出流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 3,根据流程实例ID查询流程实例
		ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		// 4,取出business_key
		String businessKey = processInstance.getBusinessKey();// LeaveBill:9
		String leaveRecordId = businessKey.split(":")[1];
		return this.leaveRecordMapper.selectByPrimaryKey(Long.valueOf(leaveRecordId));
	}

	@Override
	public List<String> queryOutComeByTaskId(String taskId) {
		List<String> names = new ArrayList<>();
		// 1,根据任务ID查询任务实例
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		// 2,取出流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		// 3,取出流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 4,根据流程实例ID查询流程实例
		ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		// 5,根据流程定义ID查询流程定义的XML信息
		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) this.repositoryService
				.getProcessDefinition(processDefinitionId);
		// 6,从流程实例对象里面取出当前活动节点ID
		String activityId = processInstance.getActivityId();// usertask1
        //获取bpmnModel对象
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        Process process = bpmnModel.getProcessById(LeaveRecord.class.getSimpleName());

        // 7,使用活动ID取出xml和当前活动ID相关节点数据
		// ActivityImpl activityImpl = processDefinition.findActivity(activityId);
		// 8,从activityImpl取出连线信息
		//List<PvmTransition> transitions = activityImpl.getOutgoingTransitions();
		/*if (null != transitions && transitions.size() > 0) {
			// PvmTransition就是连接对象
			for (PvmTransition pvmTransition : transitions) {
				String name = pvmTransition.getProperty("name").toString();
				names.add(name);
			}
		}*/
		return names;
	}

    @Override
    public PageListRes queryHistoryTask(WorkFlowVo workFlowVo,String assignee) {
        long count = historyService // 历史相关Service
                .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
                .taskAssignee(assignee.toString()).count();
        int firstResult = (workFlowVo.getPage() - 1) * workFlowVo.getLimit();
        int maxResults = workFlowVo.getLimit();
        List<HistoricTaskInstance> list = historyService // 历史相关Service
                .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
                .taskAssignee(assignee.toString())
                .listPage(firstResult, maxResults);
        return PageListResUtil.buildSuccessCountListRes(count,list);
    }

    /**
	 * 根据任务ID查询批注信息
	 */
	@Override
	public PageListRes queryCommentByTaskId(String taskId) {
		// 1,根据任务ID查询任务实例
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		// 2,从任务里面取出流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		List<Comment> comments = taskService.getProcessInstanceComments(processInstanceId);
		List<ActCommentEntity> data = new ArrayList<>();
		if (null != comments && comments.size() > 0) {
			for (Comment comment : comments) {
				ActCommentEntity entity = new ActCommentEntity();
				BeanUtils.copyProperties(comment, entity);
				data.add(entity);
			}
		}
		return PageListResUtil.buildSuccessListRes(data);
	}

	/**
	 * 完成任务
	 */
	@Override
	public void completeTask(WorkFlowVo workFlowVo) {
		String taskId = workFlowVo.getTaskId();// 任务ID
		String outcome = workFlowVo.getOutcome();// 连接名称
		Long leaveRecordId = workFlowVo.getId();// 请假单ID
		String comment = workFlowVo.getComment();// 批注信息

		// 1,根据任务ID查询任务实例
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		// 2,从任务里面取出流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 设置批注人名
        // 1,得到办理人信息
        Object principal = SecurityUtils.getSubject().getPrincipal();
        String userName = null;
        if (principal instanceof Teacher){
            userName = ((Teacher) principal).getTeacherName();
        }
        if (principal instanceof Student){
            userName = ((Student) principal).getStudentName();
        }
		/*
		 * 因为批注人是org.activiti.engine.impl.cmd.AddCommentCmd 80代码使用 String userId =
		 * Authentication.getAuthenticatedUserId(); CommentEntity comment = new
		 * CommentEntity(); comment.setUserId(userId);
		 * Authentication这类里面使用了一个ThreadLocal的线程局部变量
		 */
		Authentication.setAuthenticatedUserId(userName);
		// 添加批注信息
		this.taskService.addComment(taskId, processInstanceId, "[" + outcome + "]" + comment);
		// 完成任务
		Map<String, Object> variables = new HashMap<>();
		variables.put("outcome", outcome);
		this.taskService.complete(taskId, variables);
		// 判断任务是否结束
		ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (null == processInstance) {
			LeaveRecord leaveRecord = new LeaveRecord();
            leaveRecord.setId(leaveRecordId);
			// 说明流程结束
			if (outcome.equals("放弃")) {
                leaveRecord.setLeaveStatus(LeaveStatusConst.STATE_LEAVEBILL_THREE);// 已放弃
			} else {
                leaveRecord.setLeaveStatus(LeaveStatusConst.STATE_LEAVEBILL_TOW);// 审批完成
			}
			this.leaveRecordMapper.updateLeaveStatus(leaveRecord.getId(),leaveRecord.getLeaveStatus());
		}

	}

	@Override
	public ProcessDefinition queryProcessDefinitionByTaskId(String taskId) {
		// 1,根据任务ID查询任务实例
		Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
		// 2,取出流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 3,根据流程实例ID查询流程实例对象
		ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		// 4，取出流程部署ID
		String processDefinitionId = processInstance.getProcessDefinitionId();
		// 5,查询流程定义对象
		ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();
		return processDefinition;
	}

	/**
	 * Integer id请假单的ID
	 */
	@Override
	public PageListRes querydCommentByLeaveRecordId(Long id) {
		// 组装businesskey
		String businessKey = LeaveRecord.class.getSimpleName() + ":" + id;
		// 根据业务ID查询历史流程实例
		HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
				.processInstanceBusinessKey(businessKey).singleResult();
		// 使用taskService+流程实例ID查询批注
		List<Comment> comments = this.taskService.getProcessInstanceComments(historicProcessInstance.getId());
		List<ActCommentEntity> data = new ArrayList<>();
		if (null != comments && comments.size() > 0) {
			for (Comment comment : comments) {
				ActCommentEntity entity = new ActCommentEntity();
				BeanUtils.copyProperties(comment, entity);
				data.add(entity);
			}
		}
		return PageListResUtil.buildSuccessListRes(data);
	}

	/**
	 * 查询我的审批记录
	 */
	public PageListRes queryCurrentUserHistoryTask(WorkFlowVo workFlowVo) {
        Teacher teacher = (Teacher) SecurityUtils.getSubject().getPrincipal();
        String assignee = teacher.getTeacherName();
		int firstResult = (workFlowVo.getPage() - 1) * workFlowVo.getLimit();
		int maxResults = workFlowVo.getLimit();
        Page<Object> page = PageHelper.startPage(workFlowVo.getPage(), workFlowVo.getLimit());
		List<HistoricTaskInstance> list = this.historyService.createHistoricTaskInstanceQuery()
				.taskAssignee(assignee).listPage(firstResult, maxResults);
		return PageListResUtil.buildSuccessPageListRes(page,list);
	}
}
