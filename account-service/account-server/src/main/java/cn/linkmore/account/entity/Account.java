package cn.linkmore.account.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Account {
    private Long id;

    private Double amount;

    private Double usableAmount;

    private Double frozenAmount;

    private Double rechargeAmount;

    private Double rechagePaymentAmount;

    private Short status;

    private Double orderAmount;

    private Double orderPaymentAmount;

    private Double giftAmount;

    private Double giftTotalAmount;

    private Date createTime;

    private Date updateTime;

    private Integer accType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getUsableAmount() {
		return usableAmount;
	}

	public void setUsableAmount(Double usableAmount) {
		this.usableAmount = usableAmount;
	}

	public Double getFrozenAmount() {
		return frozenAmount;
	}

	public void setFrozenAmount(Double frozenAmount) {
		this.frozenAmount = frozenAmount;
	}

	public Double getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(Double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public Double getRechagePaymentAmount() {
		return rechagePaymentAmount;
	}

	public void setRechagePaymentAmount(Double rechagePaymentAmount) {
		this.rechagePaymentAmount = rechagePaymentAmount;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Double getOrderPaymentAmount() {
		return orderPaymentAmount;
	}

	public void setOrderPaymentAmount(Double orderPaymentAmount) {
		this.orderPaymentAmount = orderPaymentAmount;
	}

	public Double getGiftAmount() {
		return giftAmount;
	}

	public void setGiftAmount(Double giftAmount) {
		this.giftAmount = giftAmount;
	}

	public Double getGiftTotalAmount() {
		return giftTotalAmount;
	}

	public void setGiftTotalAmount(Double giftTotalAmount) {
		this.giftTotalAmount = giftTotalAmount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getAccType() {
		return accType;
	}

	public void setAccType(Integer accType) {
		this.accType = accType;
	}

}