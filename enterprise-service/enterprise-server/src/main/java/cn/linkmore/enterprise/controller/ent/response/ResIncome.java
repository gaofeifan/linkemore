package cn.linkmore.enterprise.controller.ent.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;

@ApiModel("近7-15-30收入详情列表")
public class ResIncome {

	private BigDecimal dayAmount;
	
	private Date dayTime;

	
	public ResIncome() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResIncome(BigDecimal dayAmount, Date dayTime) {
		super();
		this.dayAmount = dayAmount;
		this.dayTime = dayTime;
	}

	public BigDecimal getDayAmount() {
		return dayAmount;
	}

	public void setDayAmount(BigDecimal dayAmount) {
		this.dayAmount = dayAmount;
	}

	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getDayTime() {
		return dayTime;
	}

	public void setDayTime(Date dayTime) {
		this.dayTime = dayTime;
	}
}
