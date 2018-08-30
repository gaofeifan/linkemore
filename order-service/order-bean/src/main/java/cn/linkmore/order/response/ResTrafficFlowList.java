package cn.linkmore.order.response;

import java.util.Date;

public class ResTrafficFlowList {

	private Date date;

	private Integer carDayTotal;

	private Integer month;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getCarDayTotal() {
		return carDayTotal;
	}

	public void setCarDayTotal(Integer carDayTotal) {
		this.carDayTotal = carDayTotal;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
}
