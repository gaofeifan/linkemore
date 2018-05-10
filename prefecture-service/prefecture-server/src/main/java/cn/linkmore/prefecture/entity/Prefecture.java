package cn.linkmore.prefecture.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Prefecture {
    private Long id;

    private Long strategyId;

    private Integer status;

    private String name;

    private Long districtId;

    private String address;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String routeGuidance;

    private String imageUrl;

    private Integer stallTotal;

    private Integer soldTimes;

    private Date dateContract;

    private Date validTime;

    private Date createTime;

    private Date updateTime;

    private String routeDescription;

    private Long areasId;

    private Long baseDictId;

    private Long cityId;

    private Integer orderIndex;

    private Integer leaveTime;

    private String chargePrice;

    private Integer chargeTime;

    private Short type;

    private Long enterpriseId;

    private Short category;

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