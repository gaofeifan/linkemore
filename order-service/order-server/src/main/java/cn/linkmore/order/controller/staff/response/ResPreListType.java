package cn.linkmore.order.controller.staff.response;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车场列表类型")
public class ResPreListType implements Serializable{

	/**
	 *  
	 */ 
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("订单总数")
	private int orderConunt = 0;
	
	@ApiModelProperty("订单收入")
	private double orderIncome = 0D;
	
	@ApiModelProperty("固定总数")
	private int fixed = 0;
	
	@ApiModelProperty("固定的使用次数")
	private int fixedNumberUse = 0;
	
	@ApiModelProperty("类型  0自营  2固定  4全部 5暂无数据(默认)")
	private short type = 5;

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

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}
}
