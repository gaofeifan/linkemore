package cn.linkmore.order.request;

import java.math.BigDecimal;

/**
 * 充值面额请求bean
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
public class ReqRechargeAmount {
	private Long id;

	private BigDecimal payment;

	private BigDecimal gift;

	private Integer orderIndex;

	private Boolean checked;

	private Integer status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public BigDecimal getGift() {
		return gift;
	}

	public void setGift(BigDecimal gift) {
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
