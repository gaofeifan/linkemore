package cn.linkmore.order.controller.h5.response;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("停车订单信息")
public class ResSearch {

	@ApiModelProperty(value = "车牌")
	String plate;
	@ApiModelProperty(value = "开始时间")
	long bgTime;
	@ApiModelProperty(value = "车场信息")
	String location;
	@ApiModelProperty(value = "使用时间")
	long useTime;
	@ApiModelProperty(value = "金额")
	BigDecimal money;
	@ApiModelProperty(value = "支付记录")
	List<PayRecord>  payrecords;
	
	/**
	 * @return the payrecords
	 */
	public List<PayRecord> getPayrecords() {
		return payrecords;
	}
	/**
	 * @param payrecords the payrecords to set
	 */
	public void setPayrecords(List<PayRecord> payrecords) {
		this.payrecords = payrecords;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	
	/**
	 * @return the bgTime
	 */
	public long getBgTime() {
		return bgTime;
	}
	/**
	 * @param bgTime the bgTime to set
	 */
	public void setBgTime(long bgTime) {
		this.bgTime = bgTime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the useTime
	 */
	public long getUseTime() {
		return useTime;
	}
	/**
	 * @param useTime the useTime to set
	 */
	public void setUseTime(long useTime) {
		this.useTime = useTime;
	}
	/**
	 * @return the money
	 */
	public BigDecimal getMoney() {
		return money;
	}
	/**
	 * @param money the money to set
	 */
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
	
	
}
