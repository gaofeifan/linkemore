package cn.linkmore.order.request;

/**
 * 充值明细--请求bean
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
public class ReqWalletDetailExport {

	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 账户id
	 */
	private Long accountId;
	/**
	 * 来源:1支付宝,2微信支付，3充值赠送
	 */
	private Integer source;
	/**
	 * 分类：0消费 ，1储值
	 */
	private Integer type;
	/**
	 * 时间开始
	 */
	private String startTime;
	/**
	 * 时间结束
	 */
	private String endTime;


	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


}
