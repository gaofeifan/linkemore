package cn.linkmore.order.response;

import java.math.BigDecimal;
import java.util.List;

public class ResChargeList {

	private BigDecimal todayIncome;
	
	private List<ResCharge> details;

	public BigDecimal getTodayIncome() {
		return todayIncome;
	}

	public void setTodayIncome(BigDecimal todayIncome) {
		this.todayIncome = todayIncome;
	}

	public List<ResCharge> getDetails() {
		return details;
	}

	public void setDetails(List<ResCharge> details) {
		this.details = details;
	}

	
}
