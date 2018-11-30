package cn.linkmore.order.controller.h5.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("发起支付参数")
public class ReqPayParm {
	
	@ApiModelProperty(value = "用户身份")
	private String openId;
	@ApiModelProperty(value = "车牌")
	private String plate;
	@ApiModelProperty(value = "车场id")
	private Long preId;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public Long getPreId() {
		return preId;
	}
	public void setPreId(Long preId) {
		this.preId = preId;
	}
	

}
