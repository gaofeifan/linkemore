package cn.linkmore.order.entity;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 公司交易记录
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class CompanyTradeRecord {
    private Integer id;

    private BigDecimal totalAmount;

    private BigDecimal usableAmount;

    private BigDecimal frozenAmount;

    private BigDecimal rechargeAmount;

    private BigDecimal rechagePaymentAmount;

    private BigDecimal orderAmount;

    private BigDecimal orderPaymentAmount;

    private BigDecimal giftAmount;

    private BigDecimal giftTotalAmount;

    private Long tradeId;

    private Date createTime;

    private Date updateTime;

    private Double rechargePaymentAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
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

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
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

    public Double getRechargePaymentAmount() {
        return rechargePaymentAmount;
    }

    public void setRechargePaymentAmount(Double rechargePaymentAmount) {
        this.rechargePaymentAmount = rechargePaymentAmount;
    }
    public void copy(CompanyTradeRecord record){
		 this.orderAmount = record.getOrderAmount();
		 this.orderPaymentAmount = record.getOrderPaymentAmount();
		 this.frozenAmount = record.getFrozenAmount();
		 this.totalAmount = record.getTotalAmount();
		 this.rechargeAmount = record.getRechargeAmount();
		 this.rechargePaymentAmount = record.getRechargePaymentAmount();
		 this.usableAmount = record.getUsableAmount(); 
	}
}