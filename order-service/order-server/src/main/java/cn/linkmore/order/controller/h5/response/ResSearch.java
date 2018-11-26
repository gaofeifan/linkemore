package cn.linkmore.order.controller.h5.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("订单信息")
public class ResSearch {

	@ApiModelProperty(value = "车牌")
	String plate;
	@ApiModelProperty(value = "开始时间")
	String bgTime;
	@ApiModelProperty(value = "车场信息")
	String location;
	@ApiModelProperty(value = "使用时间")
	String useTime;
	@ApiModelProperty(value = "金额")
	String money;
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getBgTime() {
		return bgTime;
	}
	public void setBgTime(String bgTime) {
		this.bgTime = bgTime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	
	
}
