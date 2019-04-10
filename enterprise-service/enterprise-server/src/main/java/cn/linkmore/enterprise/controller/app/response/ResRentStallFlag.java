package cn.linkmore.enterprise.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("长租车位表示")
public class ResRentStallFlag {

	@ApiModelProperty(value = "查询用户是否有车位授权标识 默认false ")
	private Boolean authFlag = false;
	
	@ApiModelProperty(value = "查询用户是否有分享车位默认false")
	private Boolean shareFlag = false;

	public Boolean getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(Boolean authFlag) {
		this.authFlag = authFlag;
	}

	public Boolean getShareFlag() {
		return shareFlag;
	}

	public void setShareFlag(Boolean shareFlag) {
		this.shareFlag = shareFlag;
	}
	
	
}
