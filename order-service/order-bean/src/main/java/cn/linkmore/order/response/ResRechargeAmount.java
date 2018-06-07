package cn.linkmore.order.response;

import java.math.BigDecimal;

/**
 * 充值面额请求bean
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
public class ResRechargeAmount {
	private Long id;

	private Double payment;

	private Double gift;

	private Integer orderIndex;

	private Boolean checked;

	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public Double getGift() {
		return gift;
	}

	public void setGift(Double gift) {
		this.gift = gift;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
