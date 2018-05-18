package cn.linkmore.coupon.entity;

import java.util.Date;

public class OrderReceive {
    private Long id;

    private Long userId;

    private Long preId;

    private Short source;

    private Long grantId;

    private Double amount;

    private Date createTime;

    private Date validate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public Short getSource() {
        return source;
    }

    public void setSource(Short source) {
        this.source = source;
    }

    public Long getGrantId() {
        return grantId;
    }

    public void setGrantId(Long grantId) {
        this.grantId = grantId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getValidate() {
        return validate;
    }

    public void setValidate(Date validate) {
        this.validate = validate;
    }
}