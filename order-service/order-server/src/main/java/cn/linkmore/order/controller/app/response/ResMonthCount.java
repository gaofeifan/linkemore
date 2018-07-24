package cn.linkmore.order.controller.app.response;

import java.math.BigDecimal;

public class ResMonthCount {

	private int month;
	
	private BigDecimal monthAmount;
	
	private Integer monthCarCount;

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public BigDecimal getMonthAmount() {
		return monthAmount;
	}

	public void setMonthAmount(BigDecimal monthAmount) {
		this.monthAmount = monthAmount;
	}

	public Integer getMonthCarCount() {
		return monthCarCount;
	}

	public void setMonthCarCount(Integer monthCarCount) {
		this.monthCarCount = monthCarCount;
	}

}
