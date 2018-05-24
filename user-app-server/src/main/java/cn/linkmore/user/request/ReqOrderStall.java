package cn.linkmore.user.request;

import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("降锁请求")
public class ReqOrderStall {
	@ApiModelProperty(value = "订单ID", required = true)
	@Min(value=0,message="订单ID为大于0的长整数")
	private Long orderId;
	
	@ApiModelProperty(value = "车位ID", required = true)
	@Min(value=0,message="车位ID为大于0的长整数")
	private Long stallId;
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getStallId() {
		return stallId;
	}
	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}
	
}
