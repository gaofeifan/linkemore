package cn.linkmore.order.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ResIncome {

	private Date date;

	private BigDecimal monthAmount;
	
	private List<ResIncomeList> list;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getMonthAmount() {
		return monthAmount;
	}

	public void setMonthAmount(BigDecimal monthAmount) {
		this.monthAmount = monthAmount;
	}

	public List<ResIncomeList> getList() {
		return list;
	}

	public void setList(List<ResIncomeList> list) {
		this.list = list;
	}
	
}
