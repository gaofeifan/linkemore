package cn.linkmore.prefecture.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Prefecture {
	//状态：0，启用；1，禁用
	public static final int PRE_STATUS_ENABLE = 0;
	public static final int PRE_STATUS_DISABLE = 1;
	
	public final static short CATEGORY_COMMON = 0;
	public final static short CATEGORY_TEST = 1;
	public final static short CATEGORY_SHARE = 2;
	
	public final static short LIMIT_STATUS_FALSE = 0;
	/**
	 * 主键
	 */
    private Long id;
    /**
     * 计费策略ID
     */
    private Long strategyId;
    /**
     * 状态：0，启用；1，禁用
     */
    private Integer status;
    /**
     * 专区名称
     */
    private String name;
    /**
     * 区域id
     */
    private Long districtId;
    /**
     * 专区地址
     */
    private String address;
    /**
     * 纬度
     */
    private BigDecimal latitude;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 路线指引url
     */
    private String routeGuidance;
    /**
     * 图片URL
     */
    private String imageUrl;
    /**
     * 车位总数
     */
    private Integer stallTotal;
    /**
     * 车位已售总次数
     */
    private Integer soldTimes;
    /**
     * 签约时间
     */
    private Date dateContract;
    /**
     * 有效期
     */
    private Date validTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 线路描述
     */
    private String routeDescription;
    /**
     * areas_id(正式库中没有)
     */
    private Long areasId;
    /**
     * 字典id
     */
    private Long baseDictId;
    /**
     * 城市id
     */
    private Long cityId;
    /**
     * 专区排序指标（值大的排名靠前）
     */
    private Integer orderIndex;
    /**
     * 结账成功后提示的-离场时间(分钟)
     */
    private Integer leaveTime;
    /**
     * 计费价格
     */
    private String chargePrice;
    /**
     * 计费时间
     */
    private Integer chargeTime;
    /**
     * 专区类型(0普通，1奥迪内部定制专区)
     */
    private Short type;
    /**
     * 企业id
     */
    private Long enterpriseId;
    /**
     * 分类(0普通,1测试,2共享)
     */
    private Short category;
    /**
     * 预约受限(0不受限，1受限)
     */
    private Short limitStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getRouteGuidance() {
        return routeGuidance;
    }

    public void setRouteGuidance(String routeGuidance) {
        this.routeGuidance = routeGuidance == null ? null : routeGuidance.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Integer getStallTotal() {
        return stallTotal;
    }

    public void setStallTotal(Integer stallTotal) {
        this.stallTotal = stallTotal;
    }

    public Integer getSoldTimes() {
        return soldTimes;
    }

    public void setSoldTimes(Integer soldTimes) {
        this.soldTimes = soldTimes;
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

    public String getRouteDescription() {
        return routeDescription;
    }

    public void setRouteDescription(String routeDescription) {
        this.routeDescription = routeDescription == null ? null : routeDescription.trim();
    }

    public Long getAreasId() {
        return areasId;
    }

    public void setAreasId(Long areasId) {
        this.areasId = areasId;
    }

    public Long getBaseDictId() {
        return baseDictId;
    }

    public void setBaseDictId(Long baseDictId) {
        this.baseDictId = baseDictId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
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

    public String getChargePrice() {
        return chargePrice;
    }

    public void setChargePrice(String chargePrice) {
        this.chargePrice = chargePrice == null ? null : chargePrice.trim();
    }

    public Integer getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(Integer chargeTime) {
        this.chargeTime = chargeTime;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Short getCategory() {
        return category;
    }

    public void setCategory(Short category) {
        this.category = category;
    }

    public Short getLimitStatus() {
        return limitStatus;
    }

    public void setLimitStatus(Short limitStatus) {
        this.limitStatus = limitStatus;
    }
}