package cn.linkmore.enterprise.controller.ent.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.linkmore.order.response.ResIncomeList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询每日收入列表")
public class ResDayIncomes {

	@ApiModelProperty("日时间")
	private Date date;

	@ApiModelProperty("日总额")
	private BigDecimal dayAmount;
	
	private List<ResIncomeList> list;

	@JsonFormat(pattern="MM-dd")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getDayAmount() {
		return dayAmount;
	}

	public void setDayAmount(BigDecimal dayAmount) {
		this.dayAmount = dayAmount;
	}

	public List<ResIncomeList> getList() {
		return list;
	}

	public void setList(List<ResIncomeList> list) {
		this.list = list;
	}

	
}
