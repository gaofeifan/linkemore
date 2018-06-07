package cn.linkmore.order.response;

import java.util.Date;

/**
 * 充值明细--响应bean
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
public class ResWalletDetailExport {

	private String mobile;
	private Long accountId;
	//金额
	private Double amount;
	//历史账户余额
	private Double accountAmount;
	//来源:1支付宝,2微信支付，3充值赠送
	private String source;
	//分类：0消费 ，1储值
	private String type;
	private Date createTime;


	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Double getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(Double accountAmount) {
		this.accountAmount = accountAmount;
	}

}
