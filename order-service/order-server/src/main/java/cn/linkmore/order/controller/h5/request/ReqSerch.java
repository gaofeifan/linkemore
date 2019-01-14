package cn.linkmore.order.controller.h5.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("订单查询")
public class ReqSerch {

	@ApiModelProperty(value = "车牌")
	String plate;
	@ApiModelProperty(value = "车厂id")
	String preId;
	@ApiModelProperty(value = "用户身份")
	String openid;
	
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
	/**
	 * @return the openid
	 */
	public String getOpenid() {
		return openid;
	}
	/**
	 * @param openid the openid to set
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	
	
}
