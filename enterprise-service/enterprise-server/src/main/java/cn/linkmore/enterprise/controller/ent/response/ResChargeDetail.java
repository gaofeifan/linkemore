package cn.linkmore.enterprise.controller.ent.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("实时收费详情列表")
public class ResChargeDetail {

	@ApiModelProperty(value="车牌号")
	private String plateNo;
	
	@ApiModelProperty(value="开始时间")
	private Date startTime;
	
	@ApiModelProperty(value="结束时间")
	private Date endTime;
	
	@ApiModelProperty(value="停车时长")
	private String stopTime;
	
	@ApiModelProperty(value="订单金额")
	private BigDecimal orderAmount;
	
	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	@JsonFormat(pattern="MM:dd")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getStopTime() {
		return stopTime;
	}

	public void setStopTime(String stopTime) {
		this.stopTime = stopTime;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	@JsonFormat(pattern="MM:dd")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}

