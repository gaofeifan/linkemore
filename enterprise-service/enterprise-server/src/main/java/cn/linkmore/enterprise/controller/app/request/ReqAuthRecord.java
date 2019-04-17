package cn.linkmore.enterprise.controller.app.request;

import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModelProperty;

public class ReqAuthRecord {

	@ApiModelProperty("车位id，多个以英文,分割")
	@NotNull(message="车位id不能为空")
    private String stallIds;
	
	@ApiModelProperty("车位名称，多个以英文,分割")
	@NotNull(message="车位名称不能为空")
    private String stallNames;
	
	@ApiModelProperty("手机号")
	@NotNull(message="手机号不能为空")
    private String mobile;
	
	@ApiModelProperty("用户名称")
    private String username;
	
	@ApiModelProperty("开始时间格式 yyyy/MM/dd HH:mm")
	@NotNull(message="开始时间不能为空")
    private String startTime;
	
	@ApiModelProperty("结束时间格式 yyyy/MM/dd HH:mm")
	@NotNull(message="结束时间不能为空")
    private String endTime;
	
	@ApiModelProperty("关系id")
    private Long relationId;
	
	@ApiModelProperty("关系名称")
    private String relationName;

	@ApiModelProperty("车区id")
	@NotNull(message="车区id不能为空")
    private Long preId;

	@ApiModelProperty("车区名称")
	@NotNull(message="车区名称不能为空")
    private String preName;

	public String getStallIds() {
		return stallIds;
	}

	public void setStallIds(String stallIds) {
		this.stallIds = stallIds;
	}

	public String getStallNames() {
		return stallNames;
	}

	public void setStallNames(String stallNames) {
		this.stallNames = stallNames;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}
}
