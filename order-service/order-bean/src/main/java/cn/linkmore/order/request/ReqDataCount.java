package cn.linkmore.order.request;

public class ReqDataCount {

private Long preId;
	
	private Integer orderConunt;
	
	private double orderIncome;
	
	private Integer fixed;
	
	private Integer fixedNumberUse;
	
	private String details;
	
	private String reportForms;
	
	private String preName;
	
	private Short type;

	public Long getPreId() {
		return preId;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public Integer getOrderConunt() {
		return orderConunt;
	}

	public void setOrderConunt(Integer orderConunt) {
		this.orderConunt = orderConunt;
	}

	public double getOrderIncome() {
		return orderIncome;
	}

	public void setOrderIncome(double orderIncome) {
		this.orderIncome = orderIncome;
	}

	public Integer getFixed() {
		return fixed;
	}

	public void setFixed(Integer fixed) {
		this.fixed = fixed;
	}

	public Integer getFixedNumberUse() {
		return fixedNumberUse;
	}

	public void setFixedNumberUse(Integer fixedNumberUse) {
		this.fixedNumberUse = fixedNumberUse;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getReportForms() {
		return reportForms;
	}

	public void setReportForms(String reportForms) {
		this.reportForms = reportForms;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
	
	
}
