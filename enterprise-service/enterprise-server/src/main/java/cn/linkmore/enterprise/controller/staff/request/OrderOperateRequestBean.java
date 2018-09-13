package cn.linkmore.enterprise.controller.staff.request;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("订单操作请求封装Bean")
public class OrderOperateRequestBean  implements Serializable {
	
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 7071754330753805312L;

	@ApiModelProperty(value = "订单ID[不能为空]", required = true)
	private Long orderId;
	
	@ApiModelProperty(value = "车位ID[不能为空]", required = true)
	private Long stallId;
	
	@ApiModelProperty(value = "原因ID[能为空]", required = false)
	private Long remarkId;
	
	@ApiModelProperty(value = "原因内容[不能为空]", required = true)
	private String remark;
	
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
