package cn.linkmore.order.response;

import java.math.BigDecimal;
import java.util.Date;

public class ResIncomeList {

	private Date date;
	
	private BigDecimal dayAmount;

	private int month;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getDayAmount() {
		return dayAmount;
	}

	public void setDayAmount(BigDecimal dayAmount) {
		this.dayAmount = dayAmount;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	
}
