package cn.linkmore.third.request;

public class ReqApplePay {
	private String number;
	private Double amount;
	private Long timestramp; 
	
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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
