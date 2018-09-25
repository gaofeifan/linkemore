package cn.linkmore.order.controller.staff.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("异常订单参数")
public class ReqUnusualOrder {
	
	@ApiModelProperty(value = "车区id", required = false)
	private Long prfId;
	
	@ApiModelProperty(value = "车位id", required = false)
	private Long stallId;


	public Long getPrfId() {
		return prfId;
	}

	public void setPrfId(Long prfId) {
		this.prfId = prfId;
	}

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	
	
}
