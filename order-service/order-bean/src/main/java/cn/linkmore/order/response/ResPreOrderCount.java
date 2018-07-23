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
	 * 订单金额
	 */ 
	private BigDecimal orderAmount;

	/**
	 * 车位id
	 */ 
	private Long stallId;

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}


	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}
	
}
