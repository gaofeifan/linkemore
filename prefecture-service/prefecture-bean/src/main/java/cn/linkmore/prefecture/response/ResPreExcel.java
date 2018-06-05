package cn.linkmore.prefecture.response;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 响应-车区列表后台导出
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResPreExcel {

	private Long id;
	// 专区名称
	private String name;
	// 计费策略
	private String strategyName;
	// 首小时价格
	private String firstHourDisplay;
	// 首小时外价格
	private String basePriceDisplay;
	// 晚间价格
	private String nightPriceDisplay;
	// 签约时间
	private Date dateContract;
	// 有效期
	private Date validTime;
	// 车位总数
	private Integer stallTotal;
	// 排序等级
	private Integer orderIndex;
	// 结账离场警示时间
	private Integer leaveTime;
	// 状态 0启用 1禁用
	private Integer status;

	private Short type;
	
	private Short category;

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	/*
	 * 计费策略字段
	 */
	// 基础价格
	private BigDecimal basePrice;
	// 时间基数 计时单位
	private Integer timelyLong;
	// 首小时内
	private BigDecimal firstHour;
	// 夜间时间基数 分钟为单位
	private Integer nightTimelyLong;
	// 夜间价格
	private BigDecimal nightPrice;
	// 计时单位 分钟
	private String timelyUnit;

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
		this.name = name;
	}

	public String getStrategyName() {
		return strategyName;
	}

	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}

	public String getFirstHourDisplay() {
		return firstHourDisplay;
	}

	public void setFirstHourDisplay(String firstHourDisplay) {
		this.firstHourDisplay = firstHourDisplay;
	}

	public String getBasePriceDisplay() {
		return basePriceDisplay;
	}

	public void setBasePriceDisplay(String basePriceDisplay) {
		this.basePriceDisplay = basePriceDisplay;
	}

	public String getNightPriceDisplay() {
		return nightPriceDisplay;
	}

	public void setNightPriceDisplay(String nightPriceDisplay) {
		this.nightPriceDisplay = nightPriceDisplay;
	}

	public Date getDateContract() {
		return dateContract;
	}

	public void setDateContract(Date dateContract) {
		this.dateContract = dateContract;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Integer getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Integer leaveTime) {
		this.leaveTime = leaveTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}

	public Integer getTimelyLong() {
		return timelyLong;
	}

	public void setTimelyLong(Integer timelyLong) {
		this.timelyLong = timelyLong;
	}

	public BigDecimal getFirstHour() {
		return firstHour;
	}

	public void setFirstHour(BigDecimal firstHour) {
		this.firstHour = firstHour;
	}

	public Integer getNightTimelyLong() {
		return nightTimelyLong;
	}

	public void setNightTimelyLong(Integer nightTimelyLong) {
		this.nightTimelyLong = nightTimelyLong;
	}

	public BigDecimal getNightPrice() {
		return nightPrice;
	}

	public void setNightPrice(BigDecimal nightPrice) {
		this.nightPrice = nightPrice;
	}

	public String getTimelyUnit() {
		return timelyUnit;
	}

	public void setTimelyUnit(String timelyUnit) {
		this.timelyUnit = timelyUnit;
	}

	public Integer getStallTotal() {
		return stallTotal;
	}

	public void setStallTotal(Integer stallTotal) {
		this.stallTotal = stallTotal;
	}

	public Short getCategory() {
		return category;
	}

	public void setCategory(Short category) {
		this.category = category;
	}

}
