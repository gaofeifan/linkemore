package cn.linkmore.order.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值明细
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
public class WalletDetail {
	
	public static final Short SOURCE_ALIPAY = 1;
	public static final Short SOURCE_WECHAT = 2;
	public static final Short SOURCE_GIFT= 3;
	
	public static final Short TYPE_CONSUME = 1;
	public static final Short TYPE_RECHARGE = 2;
	
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