package cn.linkmore.prefecture.response;

import java.util.Date;

public class ResStallOps {
	private Long id;

    private String stallName;

    private Integer sellCount;

    private Long preId;

    private Long gatewayId;

    private String radar;

    private Long lockId;

    private String lockSn;

    private Integer lockStatus;

    private Integer status;

    private String stallLocal;

    private String routeGuidance;

    private Date createTime;

    private Date updateTime;

    private String imageUrl;

    private Integer lockBattery;

    private Float lockVoltage;

    private Short bindOrderStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStallName() {
        return stallName;
    }

    public void setStallName(String stallName) {
        this.stallName = stallName == null ? null : stallName.trim();
    }

    public Integer getSellCount() {
        return sellCount;
    }

    public void setSellCount(Integer sellCount) {
        this.sellCount = sellCount;
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public Long getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(Long gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getRadar() {
        return radar;
    }

    public void setRadar(String radar) {
        this.radar = radar == null ? null : radar.trim();
    }

    public Long getLockId() {
        return lockId;
    }

    public void setLockId(Long lockId) {
        this.lockId = lockId;
    }

    public String getLockSn() {
        return lockSn;
    }

    public void setLockSn(String lockSn) {
        this.lockSn = lockSn == null ? null : lockSn.trim();
    }

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStallLocal() {
        return stallLocal;
    }

    public void setStallLocal(String stallLocal) {
        this.stallLocal = stallLocal == null ? null : stallLocal.trim();
    }

    public String getRouteGuidance() {
        return routeGuidance;
    }

    public void setRouteGuidance(String routeGuidance) {
        this.routeGuidance = routeGuidance == null ? null : routeGuidance.trim();
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Integer getLockBattery() {
        return lockBattery;
    }

    public void setLockBattery(Integer lockBattery) {
        this.lockBattery = lockBattery;
    }

    public Float getLockVoltage() {
        return lockVoltage;
    }

    public void setLockVoltage(Float lockVoltage) {
        this.lockVoltage = lockVoltage;
    }

    public Short getBindOrderStatus() {
        return bindOrderStatus;
    }

    public void setBindOrderStatus(Short bindOrderStatus) {
        this.bindOrderStatus = bindOrderStatus;
    }
}
