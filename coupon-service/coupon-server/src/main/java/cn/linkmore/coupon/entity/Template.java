package cn.linkmore.coupon.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Template {
	private Long id;
	// 停车券套餐名称
	private String name;
	// 类型 0：运营 1：企业 2:拉新 3：专题停车券
	private Integer type;
	// 企业Id
	private Long enterpriseId;
	// 备注信息
	private String remark;
	// 总量
	private Integer totalQuantity;
	// 单位数量
	private Integer unitCount;
	// 单位金额
	private BigDecimal unitAmount;
	// 总金额
	private BigDecimal totalAmount;
	// 发出量
	private Integer sendQuantity;
	// 状态0未开始,1运行中,2暂停中,3已结束
	private Integer status;
	// 是否过期0未过期，1已过期
	private Integer validStatus;
	// 0 不限 1 自定义
	private Integer validType;
	// 自定义天数
	private Integer validDay;
	// 过期时间
	private Date expiryTime;
	// 开始时间
	private Date startTime;
	// 结束时间
	private Date endTime;
	// 创建人Id
	private Integer creatorId;
	// 创建人姓名
	private String creatorName;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
	// 合同金额
	private Double contractAmount;
	// 赠送金额
	private Double givenAmount;

	private Long cityId;
	private Long preId;
	private List<TemplateItem> items;// 停车券项
	private String discount;// 优惠信息
	private String remainNumber;// 套餐剩余数量

	private String enterpriseDealNumber;

	private Integer releaseMethod;

	private Integer couponExpires;

	private Integer maturityAmount;
	// 0 未回滚 1已回滚
	private Integer rollbackFlag = 0;

	private Integer deleteStatus = 0;

	private String deleteItemId;

	public Integer getRollbackFlag() {
		return rollbackFlag;
	}

	public void setRollbackFlag(Integer rollbackFlag) {
		this.rollbackFlag = rollbackFlag;
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

	/**
	 * 剩余投放期
	 */
	private String residualReleasePeriod;

	/**
	 * 投放方式为商家自定义时 企业自定义修改 0未1 已
	 */
	private Integer merchantDefault;

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public List<TemplateItem> getItems() {
		return items;
	}

	public void setItems(List<TemplateItem> items) {
		this.items = items;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getValidType() {
		return validType;
	}

	public void setValidType(Integer validType) {
		this.validType = validType;
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
		this.enterpriseDealNumber = enterpriseDealNumber;
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

	public String getResidualReleasePeriod() {
		return residualReleasePeriod;
	}

	public void setResidualReleasePeriod(String residualReleasePeriod) {
		this.residualReleasePeriod = residualReleasePeriod;
	}

	public Integer getMerchantDefault() {
		return merchantDefault;
	}

	public void setMerchantDefault(Integer merchantDefault) {
		this.merchantDefault = merchantDefault;
	}

	public String getRemainNumber() {
		return remainNumber;
	}

	public void setRemainNumber(String remainNumber) {
		this.remainNumber = remainNumber;
	}

	public Double getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	public Double getGivenAmount() {
		return givenAmount;
	}

	public void setGivenAmount(Double givenAmount) {
		this.givenAmount = givenAmount;
	}

	public Integer getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(Integer deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getDeleteItemId() {
		return deleteItemId;
	}

	public void setDeleteItemId(String deleteItemId) {
		this.deleteItemId = deleteItemId;
	}

}