package cn.linkmore.enterprise.controller.ent.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
@ApiModel("近7-15-30收入")
public class ResIncomeList {

	private BigDecimal totalAmount;
	
	private List<ResIncome> incomes;

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<ResIncome> getIncomes() {
		return incomes;
	}

	public void setIncomes(List<ResIncome> incomes) {
		this.incomes = incomes;
	}
	
	
	
}
