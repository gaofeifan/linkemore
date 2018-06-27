package cn.linkmore.coupon.response;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.linkmore.bean.common.Constants;

public class ResCoupon{ 
	private Long id;

	private BigDecimal faceAmount;
	
	private Integer type;
	
	private String name;
	
	private Short status;
	
	private Date createTime;
	
	private String preName;
	
	private String preNameList;
	
	private Short preLimit; 
	
	private Date  validTime;
	
	private Long itemId;
	
    private Long enterpriseId;
    
    private Long templateId;  
	
	private Long conditionId;
	
	private Integer discount;
	
	private Long userId;
	
	private BigDecimal conditionAmount; 
	
	private ResCondition crb;
	
	 
	public BigDecimal getFaceAmount() {
		return faceAmount;
	}

	public void setFaceAmount(BigDecimal faceAmount) {
		this.faceAmount = faceAmount;
	}

	public Integer getType() {
		if(type==null){
			type = Constants.CouponType.NORMAL.type;
		}
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() { 
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	 
	public Long getConditionId() {
		return conditionId;
	}

 
	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public BigDecimal getConditionAmount() {
		return conditionAmount;
	}

	public void setConditionAmount(BigDecimal conditionAmount) {
		this.conditionAmount = conditionAmount;
	} 
	
	public ResCondition getCrb() {
		return crb;
	}

	public void setCrb(ResCondition crb) {
		this.crb = crb;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setConditionId(Long conditionId) {
		this.conditionId = conditionId;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public String getPreNameList() {
		return preNameList;
	}

	public void setPreNameList(String preNameList) {
		this.preNameList = preNameList;
	}

	public Short getPreLimit() {
		return preLimit;
	}

	public void setPreLimit(Short preLimit) {
		this.preLimit = preLimit;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	
	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@JsonIgnore
	public String getValidate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(this.getCreateTime())+" - "+ sdf.format(this.getValidTime());
	} 
	
}
