package cn.linkmore.enterprise.controller.app.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("长租用户查看操作结果")
public class ReqWatchStatus {

	@ApiModelProperty("车位id")
	@NotNull(message="车位不能为空")
	private Long stallId;
	
	@ApiModelProperty("状态")
	@NotNull(message="状态不能为空")
	private Long status;
	
	

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}
	
	
	
}
