package cn.linkmore.coupon.response;

public class ResRollbackBean {
    private String templateName;//停车券名称
    private String serialNumber;//合同编号
    private String enterpriseName;//企业名称
    private String rollbackDate;//回滚日期
    private Double usedDealPayAmount;//回滚前已使用订单金额
    private Double userDealGiftAmount;//回滚前已使用赠送金额
    private Double givenAmount = 0d;//回滚赠送金额
    private Double contractAmount = 0d;//回滚赠送金额
    private Double afterDealPayAmount;//回滚后使用用订单金额
    private Double afterDealGiftAmount;//回滚后使用赠送金额
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public Double getUsedDealPayAmount() {
		return usedDealPayAmount;
	}
	public void setUsedDealPayAmount(Double usedDealPayAmount) {
		this.usedDealPayAmount = usedDealPayAmount;
	}
	public Double getUserDealGiftAmount() {
		return userDealGiftAmount;
	}
	public void setUserDealGiftAmount(Double userDealGiftAmount) {
		this.userDealGiftAmount = userDealGiftAmount;
	}
	public Double getGivenAmount() {
		return givenAmount;
	}
	public void setGivenAmount(Double givenAmount) {
		this.givenAmount = givenAmount;
	}
	public Double getContractAmount() {
		return contractAmount;
	}
	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}
	public String getRollbackDate() {
		return rollbackDate;
	}
	public void setRollbackDate(String rollbackDate) {
		this.rollbackDate = rollbackDate;
	}
	public Double getAfterDealPayAmount() {
		return afterDealPayAmount;
	}
	public void setAfterDealPayAmount(Double afterDealPayAmount) {
		this.afterDealPayAmount = afterDealPayAmount;
	}
	public Double getAfterDealGiftAmount() {
		return afterDealGiftAmount;
	}
	public void setAfterDealGiftAmount(Double afterDealGiftAmount) {
		this.afterDealGiftAmount = afterDealGiftAmount;
	}
    
}