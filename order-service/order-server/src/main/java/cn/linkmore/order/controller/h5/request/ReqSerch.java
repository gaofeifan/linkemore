package cn.linkmore.order.controller.h5.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("订单查询")
public class ReqSerch {

	@ApiModelProperty(value = "车牌")
	String plate;
	@ApiModelProperty(value = "车厂id")
	String preId;
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getPreId() {
		return preId;
	}
	public void setPreId(String preId) {
		this.preId = preId;
	}
	
	
	
}
