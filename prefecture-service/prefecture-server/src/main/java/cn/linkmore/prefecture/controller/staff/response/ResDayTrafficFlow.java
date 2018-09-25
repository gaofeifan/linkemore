package cn.linkmore.prefecture.controller.staff.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.linkmore.order.response.ResTrafficFlow;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询每日车流量")
public class ResDayTrafficFlow {

	@ApiModelProperty(value="月时间")
	private Date time;

	@ApiModelProperty(value="月车流量总数")
	private Integer carMonthTotal;

	@ApiModelProperty(value="月车流量列表")
	private List<ResDayTrafficFlows> trafficFlows;

	@JsonFormat(pattern="yyyy年MM月",timezone="GMT+8")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getCarMonthTotal() {
		return carMonthTotal;
	}

	public void setCarMonthTotal(Integer carMonthTotal) {
		this.carMonthTotal = carMonthTotal;
	}

	public List<ResDayTrafficFlows> getTrafficFlows() {
		return trafficFlows;
	}

	public void setTrafficFlows(List<ResDayTrafficFlows> trafficFlows) {
		this.trafficFlows = trafficFlows;
	}
	
	
}
