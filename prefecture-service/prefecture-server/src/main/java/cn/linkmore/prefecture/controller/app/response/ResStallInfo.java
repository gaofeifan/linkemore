package cn.linkmore.prefecture.controller.app.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车区车位信息")
public class ResStallInfo {
	
	@ApiModelProperty(value = "是否指定标识")
	private Boolean assignFlag = false;
	
	@ApiModelProperty(value = "车区名称")
	private String preName;
	
	@ApiModelProperty(value = "车位列表")
	private List<ResStall> stalls;

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public Boolean getAssignFlag() {
		return assignFlag;
	}

	public void setAssignFlag(Boolean assignFlag) {
		this.assignFlag = assignFlag;
	}

	public List<ResStall> getStalls() {
		return stalls;
	}

	public void setStalls(List<ResStall> stalls) {
		this.stalls = stalls;
	}
}
