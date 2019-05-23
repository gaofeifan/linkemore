package cn.linkmore.prefecture.request;

import java.util.Date;
/**
 * 请求-车位
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ReqStall {
	public static int STATUS_FREE = 1;
	public static int STATUS_USED = 2;
	public static int STATUS_PREOUTLINE = 3;
	public static int STATUS_OUTLINE = 4;
	public static int LOCK_STATUS_DOWN = 2;
	public static int LOCK_STATUS_UP = 1; 
	public static short BIND_ORDER_STATUS_NONE = 0; 
	public static short ORDER_ClOSE = 2; 
	public static short ORDER_SUSPENDED   = 1; 
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
    private Short type = 0;
    /**
     * 0普通 ，1品牌[自营属性]
     */
    private Short brand = 0;
    /**
     * 停车场内分区名称
     */
    private String areaName;
    
    /**
     * 创建用户id
     */
    private Long createUserId;
    /**
     * 创建用户名称
     */
    private String createUserName;
    
    /**
     * 创建企业id
     */
    private Long createEntId;
    /**
     * 创建企业名称
     */
    private String createEntName;
    
    /**
     * 长租车位标识一对多标识 默认0 1开启
     */
    private Short rentOmType = 0;
    /**
     * 长租车位标识多对一标识 默认0 1开启
     */
    private Short rentMoType = 0;
    /**
     * 车位所在层数如B1
     */
    private String floor;
    
    public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Short getRentOmType() {
		return rentOmType;
	}

	public void setRentOmType(Short rentOmType) {
		this.rentOmType = rentOmType;
	}

	public Short getRentMoType() {
		return rentMoType;
	}

	public void setRentMoType(Short rentMoType) {
		this.rentMoType = rentMoType;
	}

	public Long getCreateEntId() {
		return createEntId;
	}

	public void setCreateEntId(Long createEntId) {
		this.createEntId = createEntId;
	}

	public String getCreateEntName() {
		return createEntName;
	}

	public void setCreateEntName(String createEntName) {
		this.createEntName = createEntName;
	}
    
    public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
    
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
    
}