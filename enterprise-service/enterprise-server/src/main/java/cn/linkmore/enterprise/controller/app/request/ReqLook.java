package cn.linkmore.enterprise.controller.app.request;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("长租用户查看车锁状态")
public class ReqLook {
    
	@ApiModelProperty(value = "车位id", required = true)
	@NotBlank(message="车位id不能为空") 
	private Long stallId;

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	
}
