package cn.linkmore.coupon.response;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ResTemplate {
	private Long id;

    private String name;

    private String remark;

    private Integer type;

    private Long enterpriseId;

    private Integer totalQuantity;

    private Integer unitCount;

    private BigDecimal unitAmount;

    private BigDecimal totalAmount;

    private Integer sendQuantity;

    private Integer status;

    private Integer validStatus;

    private Integer validType;

    private Integer validDay;

    private Date expiryTime;

    private Date startTime;

    private Date endTime;

    private Integer creatorId;

    private String creatorName;

    private Date createTime;

    private Date updateTime;

    private String enterpriseDealNumber;

    private Integer releaseMethod;

    private Integer couponExpires;

    private Integer maturityAmount;

    private Integer merchantDefault;

    private Long cityId;

    private Long preId;

    private BigDecimal contractAmount;

    private BigDecimal givenAmount;

    private Integer deleteStatus;
    /**
     * 停车券项
     */
    private List<ResTemplateItem> items;
    /**
     * 优惠信息
     */
    private String discount;
    /**
     * 套餐剩余数量
     */
    private String remainNumber;
    
    //0 未回滚 1已回滚
    private Integer rollbackFlag =0; 
    
    /**
     * 剩余投放期
     */
    private String residualReleasePeriod;

    public Integer getRollbackFlag() {
		return rollbackFlag;
	}

	public void setRollbackFlag(Integer rollbackFlag) {
		this.rollbackFlag = rollbackFlag;
	}

	public String getResidualReleasePeriod() {
		return residualReleasePeriod;
	}

	public void setResidualReleasePeriod(String residualReleasePeriod) {
		this.residualReleasePeriod = residualReleasePeriod;
	}

	public List<ResTemplateItem> getItems() {
		return items;
	}

	public void setItems(List<ResTemplateItem> items) {
		this.items = items;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getRemainNumber() {
		return remainNumber;
	}

	public void setRemainNumber(String remainNumber) {
		this.remainNumber = remainNumber;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Integer getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(Integer unitCount) {
        this.unitCount = unitCount;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getSendQuantity() {
        return sendQuantity;
    }

    public void setSendQuantity(Integer sendQuantity) {
        this.sendQuantity = sendQuantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public Integer getValidType() {
        return validType;
    }

    public void setValidType(Integer validType) {
        this.validType = validType;
    }

    public Integer getValidDay() {
        return validDay;
    }

    public void setValidDay(Integer validDay) {
        this.validDay = validDay;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEnterpriseDealNumber() {
        return enterpriseDealNumber;
    }

    public void setEnterpriseDealNumber(String enterpriseDealNumber) {
        this.enterpriseDealNumber = enterpriseDealNumber == null ? null : enterpriseDealNumber.trim();
    }

    public Integer getReleaseMethod() {
        return releaseMethod;
    }

    public void setReleaseMethod(Integer releaseMethod) {
        this.releaseMethod = releaseMethod;
    }

    public Integer getCouponExpires() {
        return couponExpires;
    }

    public void setCouponExpires(Integer couponExpires) {
        this.couponExpires = couponExpires;
    }

    public Integer getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(Integer maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    public Integer getMerchantDefault() {
        return merchantDefault;
    }

    public void setMerchantDefault(Integer merchantDefault) {
        this.merchantDefault = merchantDefault;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public BigDecimal getGivenAmount() {
        return givenAmount;
    }

    public void setGivenAmount(BigDecimal givenAmount) {
        this.givenAmount = givenAmount;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

}