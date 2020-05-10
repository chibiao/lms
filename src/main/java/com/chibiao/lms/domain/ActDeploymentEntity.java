package com.chibiao.lms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
@Data
public class ActDeploymentEntity {
	private String id;
	private String name;
	private String category;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date deploymentTime;
}
