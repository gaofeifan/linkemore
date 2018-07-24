package cn.linkmore.prefecture.entity;

import java.util.Date;
/**
 * entity 车位
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class Stall { 
	public static final int STATUS_FREE = 1;
	public static final int STATUS_BUSY = 2; 
	public static final int STATUS_OFFLINED = 4;
	public static final int STATUS_FAULT = 5;
	/**
	 * 主键
	 */
    private Long id;
    /**
	 * 车位名称
	 */
    private String stallName;
    /**
	 * 已售车位次数
	 */
    private Integer sellCount;
    /**
	 * 专区id
	 */
    private Long preId;
    /**
	 * 网关id
	 */
    private Long gatewayId;
    /**
	 * 雷达
	 */
    private String radar;
    /**
	 * 车位锁主键
	 */
    private Long lockId;
    /**
	 * 车位锁序列号
	 */
    private String lockSn;
    /**
	 * 车位锁操作状态：1，升起；2，降下
	 */
    private Integer lockStatus;
    /**
	 * 状态:1，空闲；2，使用中；3,预下线；4，下线
	 */
    private Integer status;
    /**
	 * 车位位置描述
	 */
    private String stallLocal;
    /**
	 * 路线指引
	 */
    private String routeGuidance;
    /**
	 * 创建时间
	 */
    private Date createTime;
    /**
	 * 更新时间
	 */
    private Date updateTime;
    /**
	 * 车位导航图
	 */
    private String imageUrl;
    /**
	 * 电量百分数
	 */
    private Integer lockBattery;
    /**
	 * 车位锁电压
	 */
    private Double lockVoltage;
    /**
	 * 绑定订单状态
	 */
    private Short bindOrderStatus;
    
    /**
     * 分类0自营，1临停，2长租，3VIP
     */
    private Short type;
    /**
     * 0普通 ，1品牌[自营属性]
     */
    private Short brand;
    
    public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public Short getBrand() {
		return brand;
	}

	public void setBrand(Short brand) {
		this.brand = brand;
	}

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

    public Double getLockVoltage() {
        return lockVoltage;
    }

    public void setLockVoltage(Double lockVoltage) {
        this.lockVoltage = lockVoltage;
    }

    public Short getBindOrderStatus() {
        return bindOrderStatus;
    }

    public void setBindOrderStatus(Short bindOrderStatus) {
        this.bindOrderStatus = bindOrderStatus;
    }
}