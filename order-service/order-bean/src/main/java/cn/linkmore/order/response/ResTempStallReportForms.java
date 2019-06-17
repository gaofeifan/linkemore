package cn.linkmore.order.response;

import java.util.Date;

public class ResTempStallReportForms {

	private int orderNumber;
	
	private String orderNumberPercent;
	
	private double orderIncome;
	
	private String orderIncomePercent;
	
	private Double orderAdvanceIncome;
	
	private String orderAdvancePercent;
	private String orderAdvanceIncomePercent;
	private Double orderScanIncome;
	private String orderScanIncomePercent;
	private String orderScanPercent;
	private Double orderShareIncome;
	private String orderSharePercent;
	private String orderShareIncomePercent;
	
	private Double orderUseDuration;
	
	private String orderUseDurationPercent;
	
	private Date startTime;
	
	private int orderAdvanceNumber;   
	private int orderScanNumber;     
	private int orderShareNumber;
	
	private Double onceStallUserTime;
	
	private Double useDurationTimestamp;
	private Double useDurationContrastTimestamp;
	
	private Double onceStallUserTimestamp;
	private Double onceStallUserContrastTimestamp;
	

	public Double getUseDurationTimestamp() {
		return useDurationTimestamp;
	}
	public void setUseDurationTimestamp(Double useDurationTimestamp) {
		this.useDurationTimestamp = useDurationTimestamp;
	}
	public Double getUseDurationContrastTimestamp() {
		return useDurationContrastTimestamp;
	}
	public void setUseDurationContrastTimestamp(Double useDurationContrastTimestamp) {
		this.useDurationContrastTimestamp = useDurationContrastTimestamp;
	}
	public Double getOnceStallUserTimestamp() {
		return onceStallUserTimestamp;
	}
	public void setOnceStallUserTimestamp(Double onceStallUserTimestamp) {
		this.onceStallUserTimestamp = onceStallUserTimestamp;
	}
	public Double getOnceStallUserContrastTimestamp() {
		return onceStallUserContrastTimestamp;
	}
	public void setOnceStallUserContrastTimestamp(Double onceStallUserContrastTimestamp) {
		this.onceStallUserContrastTimestamp = onceStallUserContrastTimestamp;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderNumberPercent() {
		return orderNumberPercent;
	}

	public void setOrderNumberPercent(String orderNumberPercent) {
		this.orderNumberPercent = orderNumberPercent;
	}

	public double getOrderIncome() {
		return orderIncome;
	}
	public int getOrderAdvanceNumber() {
		return orderAdvanceNumber;
	}
	public void setOrderAdvanceNumber(int orderAdvanceNumber) {
		this.orderAdvanceNumber = orderAdvanceNumber;
	}
	public int getOrderScanNumber() {
		return orderScanNumber;
	}
	public void setOrderScanNumber(int orderScanNumber) {
		this.orderScanNumber = orderScanNumber;
	}
	public int getOrderShareNumber() {
		return orderShareNumber;
	}
	public void setOrderShareNumber(int orderShareNumber) {
		this.orderShareNumber = orderShareNumber;
	}
	public void setOrderIncome(double orderIncome) {
		this.orderIncome = orderIncome;
	}

	public String getOrderIncomePercent() {
		return orderIncomePercent;
	}

	public void setOrderIncomePercent(String orderIncomePercent) {
		this.orderIncomePercent = orderIncomePercent;
	}

	public Double getOrderAdvanceIncome() {
		return orderAdvanceIncome;
	}

	public void setOrderAdvanceIncome(Double orderAdvanceIncome) {
		this.orderAdvanceIncome = orderAdvanceIncome;
	}

	public String getOrderAdvanceIncomePercent() {
		return orderAdvanceIncomePercent;
	}

	public void setOrderAdvanceIncomePercent(String orderAdvanceIncomePercent) {
		this.orderAdvanceIncomePercent = orderAdvanceIncomePercent;
	}

	public Double getOrderScanIncome() {
		return orderScanIncome;
	}

	public void setOrderScanIncome(Double orderScanIncome) {
		this.orderScanIncome = orderScanIncome;
	}

	public String getOrderScanIncomePercent() {
		return orderScanIncomePercent;
	}

	public void setOrderScanIncomePercent(String orderScanIncomePercent) {
		this.orderScanIncomePercent = orderScanIncomePercent;
	}

	public Double getOrderShareIncome() {
		return orderShareIncome;
	}

	public void setOrderShareIncome(Double orderShareIncome) {
		this.orderShareIncome = orderShareIncome;
	}

	public String getOrderShareIncomePercent() {
		return orderShareIncomePercent;
	}

	public void setOrderShareIncomePercent(String orderShareIncomePercent) {
		this.orderShareIncomePercent = orderShareIncomePercent;
	}

	public Double getOrderUseDuration() {
		return orderUseDuration;
	}

	public void setOrderUseDuration(Double orderUseDuration) {
		this.orderUseDuration = orderUseDuration;
	}

	public String getOrderUseDurationPercent() {
		return orderUseDurationPercent;
	}

	public void setOrderUseDurationPercent(String orderUseDurationPercent) {
		this.orderUseDurationPercent = orderUseDurationPercent;
	}
	public String getOrderAdvancePercent() {
		return orderAdvancePercent;
	}
	public void setOrderAdvancePercent(String orderAdvancePercent) {
		this.orderAdvancePercent = orderAdvancePercent;
	}
	public String getOrderScanPercent() {
		return orderScanPercent;
	}
	public void setOrderScanPercent(String orderScanPercent) {
		this.orderScanPercent = orderScanPercent;
	}
	public String getOrderSharePercent() {
		if(orderSharePercent == null) {
			return "0.00%";
		}
		return orderSharePercent;
	}
	public void setOrderSharePercent(String orderSharePercent) {
		this.orderSharePercent = orderSharePercent;
	}
	public Double getOnceStallUserTime() {
		return onceStallUserTime;
	}
	public void setOnceStallUserTime(Double onceStallUserTime) {
		this.onceStallUserTime = onceStallUserTime;
	}
}
