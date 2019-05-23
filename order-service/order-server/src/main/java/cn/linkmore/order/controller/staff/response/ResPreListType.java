package cn.linkmore.order.controller.staff.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车场列表类型")
public class ResPreListType {

	@ApiModelProperty("订单总数")
	private int orderConunt = 0;
	
	@ApiModelProperty("订单收入")
	private double orderIncome = 0D;
	
	@ApiModelProperty("固定总数")
	private int fixed = 0;
	
	@ApiModelProperty("固定的使用次数")
	private int fixedNumberUse = 0;

	public int getOrderConunt() {
		return orderConunt;
	}

	public void setOrderConunt(int orderConunt) {
		this.orderConunt = orderConunt;
	}

	public double getOrderIncome() {
		return orderIncome;
	}

	public void setOrderIncome(double orderIncome) {
		this.orderIncome = orderIncome;
	}

	public int getFixed() {
		return fixed;
	}

	public void setFixed(int fixed) {
		this.fixed = fixed;
	}

	public int getFixedNumberUse() {
		return fixedNumberUse;
	}

	public void setFixedNumberUse(int fixedNumberUse) {
		this.fixedNumberUse = fixedNumberUse;
	}
	
}
