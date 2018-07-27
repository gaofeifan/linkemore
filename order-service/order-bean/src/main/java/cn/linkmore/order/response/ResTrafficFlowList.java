package cn.linkmore.order.response;

import java.util.Date;

public class ResTrafficFlowList {

	private Date date;

	private Integer carDayTotal;

	private int month;

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

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

}
