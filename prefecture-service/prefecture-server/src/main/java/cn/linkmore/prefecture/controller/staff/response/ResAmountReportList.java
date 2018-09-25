package cn.linkmore.prefecture.controller.staff.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("近7-15-30收入详情列表")
public class ResAmountReportList {
	@ApiModelProperty("天金额")
	private BigDecimal dayAmount;
	
	@ApiModelProperty("天时间")
	private Date dayTime;

	
	public ResAmountReportList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResAmountReportList(BigDecimal dayAmount, Date dayTime) {
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

	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getDayTime() {
		return dayTime;
	}

	public void setDayTime(Date dayTime) {
		this.dayTime = dayTime;
	}
}
