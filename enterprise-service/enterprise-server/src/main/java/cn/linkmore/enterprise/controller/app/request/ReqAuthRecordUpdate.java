package cn.linkmore.enterprise.controller.app.request;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;

public class ReqAuthRecordUpdate {

	@ApiModelProperty("主键id")
	@NotNull(message="主键id不能为空")
    private Long id;
	
	@ApiModelProperty("用户名称")
    private String username;
	
	@ApiModelProperty("开始时间格式 yyyy/MM/dd hh:mm")
	@NotNull(message="开始时间不能为空")
    private String startTime;
	
	@ApiModelProperty("结束时间格式 yyyy/MM/dd hh:mm")
	@NotNull(message="结束时间不能为空")
    private String endTime;
	
	@ApiModelProperty("关系id")
    private Long relationId;
	
	@ApiModelProperty("关系名称")
    private String relationName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
	
}
