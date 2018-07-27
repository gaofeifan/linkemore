package cn.linkmore.order.response;

import java.math.BigDecimal;
import java.util.Date;

public class ResPreDataList {

	private BigDecimal dayAmount;
	
	private Integer dayNumber;
	
	private Date dayTime;

	public BigDecimal getDayAmount() {
		return dayAmount;
	}

	public void setDayAmount(BigDecimal dayAmount) {
		this.dayAmount = dayAmount;
	}

	public Integer getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(Integer dayNumber) {
		this.dayNumber = dayNumber;
	}

	public Date getDayTime() {
		return dayTime;
	}

	public void setDayTime(Date dayTime) {
		this.dayTime = dayTime;
	}
	
}
