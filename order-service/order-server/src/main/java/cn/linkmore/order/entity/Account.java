package cn.linkmore.order.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 账户信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class Account {
    private Long id;

    private BigDecimal amount;

    private BigDecimal usableAmount;

    private BigDecimal frozenAmount;

    private BigDecimal rechargeAmount;

    private BigDecimal rechagePaymentAmount;

    private Short status;

    private BigDecimal orderAmount;

    private BigDecimal orderPaymentAmount;

    private BigDecimal giftAmount;

    private BigDecimal giftTotalAmount;

    private Date createTime;

    private Date updateTime;

    private Integer accType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUsableAmount() {
        return usableAmount;
    }

    public void setUsableAmount(BigDecimal usableAmount) {
        this.usableAmount = usableAmount;
    }

    public BigDecimal getFrozenAmount() {
        return frozenAmount;
    }

    public void setFrozenAmount(BigDecimal frozenAmount) {
        this.frozenAmount = frozenAmount;
    }

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public BigDecimal getRechagePaymentAmount() {
        return rechagePaymentAmount;
    }

    public void setRechagePaymentAmount(BigDecimal rechagePaymentAmount) {
        this.rechagePaymentAmount = rechagePaymentAmount;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public BigDecimal getOrderPaymentAmount() {
        return orderPaymentAmount;
    }

    public void setOrderPaymentAmount(BigDecimal orderPaymentAmount) {
        this.orderPaymentAmount = orderPaymentAmount;
    }

    public BigDecimal getGiftAmount() {
        return giftAmount;
    }

    public void setGiftAmount(BigDecimal giftAmount) {
        this.giftAmount = giftAmount;
    }

    public BigDecimal getGiftTotalAmount() {
        return giftTotalAmount;
    }

    public void setGiftTotalAmount(BigDecimal giftTotalAmount) {
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