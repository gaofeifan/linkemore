package cn.linkmore.order.controller.app.request;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Range;

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
	
	@ApiModelProperty(value = "到达状态[0未知，1已到达，2未到达]", required = true) 
	@Range(min=0, max=3,message="到达状态只能为0，1，2的数")
	private Short parkingStatus;
	
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
	public Short getParkingStatus() {
		return parkingStatus;
	}
	public void setParkingStatus(Short parkingStatus) {
		this.parkingStatus = parkingStatus;
	} 
}
