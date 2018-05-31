package cn.linkmore.third.pay.alipay;

/**
 * 支付宝同步通知json实体
 * @author duhq
 *
 */
public class AppPayResult {

	private String resultStatus;
	
	private String result;
	
	private String memo;
	
	//订单id
	private Long id;
	
	private String rechargeCode;

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRechargeCode() {
		return rechargeCode;
	}

	public void setRechargeCode(String rechargeCode) {
		this.rechargeCode = rechargeCode;
	} 
}
