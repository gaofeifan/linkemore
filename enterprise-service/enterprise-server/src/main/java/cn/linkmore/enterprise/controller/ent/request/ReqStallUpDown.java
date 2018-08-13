package cn.linkmore.enterprise.controller.ent.request;

import javax.validation.constraints.NotNull;

import cn.linkmore.annotation.Digits;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车位上下线")
public class ReqStallUpDown {

	@ApiModelProperty(required=true,value="车位id")
	@NotNull(message="车位不能为空")
	@Digits(message="车位应为整数")
	private Long stallId;
	
	@ApiModelProperty(required=true,value="下线原因id")
	@NotNull(message="下线原因不能为null")
	@Digits(message="车位原因应为整数")
	private Long remarkId;
	
	@ApiModelProperty(required=true,value="下线原因")
	private String remark;

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public Long getRemarkId() {
		return remarkId;
	}

	public void setRemarkId(Long remarkId) {
		this.remarkId = remarkId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
