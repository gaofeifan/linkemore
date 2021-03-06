package cn.linkmore.enterprise.controller.ent.response;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("车区每日统计列表")
public class ResPreOrderCount {

	/**
	 * 车区名称
	 */
	@ApiModelProperty(value="车区名称")
	private String preName;
	
	/**
	 * 车区id
	 */
	@ApiModelProperty(value="车区主键")
	private Long preId;
	
	/**
	 * 今日收入
	 */ 
	@ApiModelProperty(value="今日收入金额")
	private BigDecimal dayAmount;

	/**
	 * 今日订单
	 */ 
	@ApiModelProperty(value="今日订单数量")
	private Integer dayOrder;

	public String getPreName() {
		return preName;
	}


	public Long getPreId() {
		return preId;
	}


	public void setPreId(Long preId) {
		this.preId = preId;
	}


	public void setPreName(String preName) {
		this.preName = preName;
	}


	public BigDecimal getDayAmount() {
		return dayAmount;
	}

	public void setDayAmount(BigDecimal dayAmount) {
		this.dayAmount = dayAmount;
	}

	public Integer getDayOrder() {
		return dayOrder;
	}

	public void setDayOrder(Integer dayOrder) {
		this.dayOrder = dayOrder;
	}

}
