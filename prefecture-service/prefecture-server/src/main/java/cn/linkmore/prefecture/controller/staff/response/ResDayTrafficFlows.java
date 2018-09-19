package cn.linkmore.prefecture.controller.staff.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询每日车流量")
public class ResDayTrafficFlows {
	
	@ApiModelProperty(value="日时间")
	private Date date;
	
	@ApiModelProperty(value="日车流量总数")
	private Integer carDayTotal;

	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")	
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
}
