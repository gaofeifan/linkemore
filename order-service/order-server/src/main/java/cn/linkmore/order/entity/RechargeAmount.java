package cn.linkmore.order.entity;

import java.math.BigDecimal;

import cn.linkmore.annotation.GColumn;
import cn.linkmore.annotation.GTable;

/**
 * 充值面额
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
@GTable(vlaue="t_acc_recharge_amount")
public class RechargeAmount {
	@GColumn
    private Long id;

	@GColumn
    private BigDecimal payment;

	@GColumn
    private BigDecimal gift;

	@GColumn
    private Integer orderIndex;

	@GColumn
    private Boolean checked;

	@GColumn
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
    
}