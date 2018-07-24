package cn.linkmore.enterprise.controller.ent.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ResIncome {

	private Date time;
	
	private BigDecimal monthAmount;
	
	private List<ResIncome> incomes;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public BigDecimal getMonthAmount() {
		return monthAmount;
	}

	public void setMonthAmount(BigDecimal monthAmount) {
		this.monthAmount = monthAmount;
	}

	public List<ResIncome> getIncomes() {
		return incomes;
	}

	public void setIncomes(List<ResIncome> incomes) {
		this.incomes = incomes;
	}
}
