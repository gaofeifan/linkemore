package cn.linkmore.order.controller.app.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 车位切换封装
 * @author liwenlong
 * @version 2.0
 *
 */
@ApiModel("车位切换请求")
public class ReqSwitch {
	@ApiModelProperty(value = "订单ID", required = true)
	@Min(value=0,message="订单ID为大于0的长整数")
	@NotBlank(message="订单ID不能为空") 
	private Long orderId;
	
	@ApiModelProperty(value = "原因ID", required = true)
	@Min(value=0,message="原因ID为大于0的长整数")
	@NotBlank(message="原因ID不能为空") 
	private Long causeId;
	
	@ApiModelProperty(value = "其它原因", required = true) 
	private String remark;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getCauseId() {
		return causeId;
	}
	public void setCauseId(Long causeId) {
		this.causeId = causeId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	} 
}
