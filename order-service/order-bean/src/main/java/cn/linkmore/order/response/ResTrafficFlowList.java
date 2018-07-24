package cn.linkmore.order.response;

import java.util.Date;
import java.util.List;

public class ResTrafficFlowList {

	private Date time;
	
	private Integer carMonthTotal;
	
	private int month;
	
	private List<ResTrafficFlow> trafficFlows;

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

	public List<ResTrafficFlow> getTrafficFlows() {
		return trafficFlows;
	}

	public void setTrafficFlows(List<ResTrafficFlow> trafficFlows) {
		this.trafficFlows = trafficFlows;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}
	
	
}
