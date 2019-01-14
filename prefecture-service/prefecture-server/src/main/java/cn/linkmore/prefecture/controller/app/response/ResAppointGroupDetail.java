package cn.linkmore.prefecture.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车区预约分组详情信息")
public class ResAppointGroupDetail {
	
	@ApiModelProperty(value = "是否指定标识")
	private Boolean assignFlag = false;
	
	@ApiModelProperty(value = "车区名称")
	private String preName;
	
	@ApiModelProperty(value = "分组策略详情")
	private ResGroupStrategy groupStrategy;

	public Boolean getAssignFlag() {
		return assignFlag;
	}

	public void setAssignFlag(Boolean assignFlag) {
		this.assignFlag = assignFlag;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public ResGroupStrategy getGroupStrategy() {
		return groupStrategy;
	}

	public void setGroupStrategy(ResGroupStrategy groupStrategy) {
		this.groupStrategy = groupStrategy;
	}

	
}
