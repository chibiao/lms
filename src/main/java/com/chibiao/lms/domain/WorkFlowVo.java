package com.chibiao.lms.domain;

import lombok.Data;

@Data
public class WorkFlowVo {
	
	//批量删除使用
	private String[] ids;
	
	private Integer page;
	private Integer limit;
	
	//流程部署名称
	private String deploymentName;
	//流程部署ID
	private String deploymentId;
	//请假单ID
	private Long id;
	//任务ID
	private String taskId;
	//连接名称
	private String outcome;
	//批注信息
	private String comment;
}
