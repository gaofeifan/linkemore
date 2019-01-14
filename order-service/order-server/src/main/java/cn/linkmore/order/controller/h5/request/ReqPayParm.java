package cn.linkmore.order.controller.h5.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("发起支付参数")
public class ReqPayParm {
	
	@ApiModelProperty(value = "用户身份")
	private String openid;
	@ApiModelProperty(value = "车牌")
	private String plate;
	@ApiModelProperty(value = "车场id")
	private Long preId;
	

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
