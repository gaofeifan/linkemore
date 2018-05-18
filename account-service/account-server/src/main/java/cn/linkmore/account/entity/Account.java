package cn.linkmore.account.entity;

import java.util.Date;

/**
 * 账户
 * @author   GFF
 * @Date     2018年5月18日
 * @Version  v2.0
 */
public class Account {
    private Long id;

    /**
     *  总金额
     */ 
    private Double amount;

    /**
     *  可用金额
     */ 
    private Double usableAmount;

    /**
     *  冻结金额
     */ 
    private Double frozenAmount;

    /**
     *  充值金额
     */ 
    private Double rechargeAmount;

    /**
     *  实际充值金额
     */ 
    private Double rechagePaymentAmount;

    /**
     *  账户状态: 0 启用；1禁用
     */ 
    private Short status;

    /**
     *  订单消费总金额
     */ 
    private Double orderAmount;

    /**
     *  实际支付金额
     */ 
    private Double orderPaymentAmount;

    /**
     *  赠送余额
     */ 
    private Double giftAmount;

    /**
     *  赠送总额
     */ 
    private Double giftTotalAmount;

    private Date createTime;

    private Date updateTime;

    /**
     *  账户分类
     */ 
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