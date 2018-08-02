package cn.linkmore.enterprise.controller.ent.response;

import java.math.BigDecimal;
import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("实时收费列表")
public class ResChargeList {
	
	@ApiModelProperty(value="总金额")
	private BigDecimal todayIncome;

	@ApiModelProperty(value="收费时间列表")
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
