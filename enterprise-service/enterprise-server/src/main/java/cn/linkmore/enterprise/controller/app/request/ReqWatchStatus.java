package cn.linkmore.enterprise.controller.app.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("长租用户查看操作结果")
public class ReqWatchStatus {

	@ApiModelProperty("车位id")
	@NotNull(message="车位不能为空")
	private Long stallId;

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}
	
	
	
}
