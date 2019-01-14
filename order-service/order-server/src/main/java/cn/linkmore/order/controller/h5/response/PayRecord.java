package cn.linkmore.order.controller.h5.response;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("已支付过得记录")
public class PayRecord {

	@ApiModelProperty(value = "支付完成时间")
	Date finishTime;
	@ApiModelProperty(value = "金额")
	BigDecimal money;
	/**
	 * @return the finishTime
	 */
	public Date getFinishTime() {
		return finishTime;
	}
	/**
	 * @param finishTime the finishTime to set
	 */
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
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
