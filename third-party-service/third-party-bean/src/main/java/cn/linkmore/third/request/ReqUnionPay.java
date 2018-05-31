package cn.linkmore.third.request;

public class ReqUnionPay {
	private String code;
	private Double amount;
	private Long timestramp;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getTimestramp() {
		return timestramp;
	}
	public void setTimestramp(Long timestramp) {
		this.timestramp = timestramp;
	}
	
}
