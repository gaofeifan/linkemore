package cn.linkmore.enterprise.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("授权车位锁信息")
public class AuthRecordDetail {
	
	@ApiModelProperty(value = "主键Id")
	private Long id;
	
	@ApiModelProperty(value = "手机号")
	private String mobile;
	
	@ApiModelProperty(value = "关系id")
	private Long relationId;
	
	@ApiModelProperty(value = "关系名称")
	private String relationName;
	
	@ApiModelProperty(value = "用户名")
	private String username;
	
	@ApiModelProperty(value = "授权状态： 0授权中 1已取消 2已失效")
	private Short authFlag;
	
	@ApiModelProperty(value = "车位开始时间")
	private String startTime;

	@ApiModelProperty(value = "车位结束时间")
	private String endTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Short getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(Short authFlag) {
		this.authFlag = authFlag;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
