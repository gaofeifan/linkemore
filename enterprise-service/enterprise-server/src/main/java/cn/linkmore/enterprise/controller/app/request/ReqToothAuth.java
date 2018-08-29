package cn.linkmore.enterprise.controller.app.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("操作蓝牙许可")
public class ReqToothAuth {

	@ApiModelProperty("车位id")
	@NotNull(message="车位不能为空")
	private Long stallId;
	
	@ApiModelProperty("用户id")
	@NotNull(message="用户id不为空")
	private Long userId;

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
}
