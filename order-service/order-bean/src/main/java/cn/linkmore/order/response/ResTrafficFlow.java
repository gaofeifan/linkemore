package cn.linkmore.order.response;
import java.util.Date;
import java.util.List;
public class ResTrafficFlow {
	
	private Date time;
	
	private Integer carMonthTotal;
	
	private List<ResTrafficFlowList> trafficFlows;

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

	public List<ResTrafficFlowList> getTrafficFlows() {
		return trafficFlows;
	}

	public void setTrafficFlows(List<ResTrafficFlowList> trafficFlows) {
		this.trafficFlows = trafficFlows;
	}

}
