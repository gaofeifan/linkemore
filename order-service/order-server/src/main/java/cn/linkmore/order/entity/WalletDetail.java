package cn.linkmore.order.entity;

import java.math.BigDecimal;
import java.util.Date;

public class WalletDetail {
    private Long id;

    private Long accountId;

    private BigDecimal amount;

    private Short source;

    private Short type;

    private Date createTime;

    private BigDecimal accountAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Short getSource() {
        return source;
    }

    public void setSource(Short source) {
        this.source = source;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getAccountAmount() {
        return accountAmount;
    }

    public void setAccountAmount(BigDecimal accountAmount) {
        this.accountAmount = accountAmount;
    }
}