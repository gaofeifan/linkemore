package cn.linkmore.enterprise.controller.ent.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.linkmore.order.response.ResIncomeList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询每日收费")
public class ResDayIncome {

	@ApiModelProperty("月时间")
	private Date date;

	@ApiModelProperty("月总额")
	private BigDecimal monthAmount;
	
	private List<ResDayIncomes> list;

	@JsonFormat(pattern="yyyy年MM月",timezone="GMT+8")
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

	public List<ResDayIncomes> getList() {
		return list;
	}

	public void setList(List<ResDayIncomes> list) {
		this.list = list;
	}
	
}
