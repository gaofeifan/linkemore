package cn.linkmore.prefecture.controller.staff.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("近7-15-30日车流量详情列表")
public class ResTrafficFlowList {

	@ApiModelProperty(value="时间")
	private Date dayTime;
	
	@ApiModelProperty(value="每天数量")
	private Integer dayNumber;

	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getDayTime() {
		return dayTime;
	}

	public void setDayTime(Date dayTime) {
		this.dayTime = dayTime;
	}

	public Integer getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(Integer dayNumber) {
		this.dayNumber = dayNumber;
	}
	
	
}
