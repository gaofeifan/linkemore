package cn.linkmore.order.response;

import java.math.BigDecimal;

/**
 * @author   GFF
 * @Date     2018年7月21日
 * @Version  v2.0
 */
public class ResPreOrderCount {
	

	/**
	 * 车区名称
	 */
	private String preName;
	
	/**
	 * 车区id
	 */
	private Long preId;
	
	/**
	 * 今日总额
	 */ 
	private BigDecimal dayAmount;

	/**
	 * 今日订单
	 */ 
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
