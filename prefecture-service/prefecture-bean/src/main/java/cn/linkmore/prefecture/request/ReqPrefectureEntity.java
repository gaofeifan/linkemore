package cn.linkmore.prefecture.request;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 请求-车区信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ReqPrefectureEntity {
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
    private Integer status = 1;
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
    private Short type = 0;
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
    /**
     * 网关分组
     */
    private String gateway;
    
    /**
     * 运营时长 （分钟数）
     */
    private Integer runtime;
    /**
     * 月租金成本（元）
     */
    private Integer monthRent;
    /**
     * 租金涨幅 百分比
     */
    private String increase;
    /** 
     * 计费策略描述
     */
    private String strategyDescription;
    /**
     * 版本
     */
    private String version;
    /**
     * 创建用户Id
     */
    private Long createUserId;
    /**
     * 创建用户名称
     */
    private String createUserName;
    /**
     * 营业时长8:00 - 20:00
     */
    private String businessTime;
    /**
     * 网格横坐标
     */
    private Integer gridX;
    /**
     * 网格纵坐标
     */
    private Integer gridY;
    
    /**
     * 区域地面、地下
     */
    private String region;
    /**
     * 地下层级B2-B5
     */
    private String underLayer;
    /**
     * 总车位数
     */
    private Integer totalStallNum;
    
    /**
     * 车区类型，1临停车区（包含临停和固定车位） 2固定车区（全部为固定车位）
     */
    private Short preType;
    /**
     * 创建商家id
     */
    private Long createEntId;
    
    /**
     * 创建商家名称
     */
    private String createEntName;
    
    /**
     * 路径规划标识(0禁用，1开启)
     */
    private Short pathFlag;
    
    public Short getPathFlag() {
		return pathFlag;
	}
	public void setPathFlag(Short pathFlag) {
		this.pathFlag = pathFlag;
	}
    
    public String getCreateEntName() {
		return createEntName;
	}

	public void setCreateEntName(String createEntName) {
		this.createEntName = createEntName;
	}

	public Long getCreateEntId() {
		return createEntId;
	}

	public void setCreateEntId(Long createEntId) {
		this.createEntId = createEntId;
	}

	public Short getPreType() {
		return preType;
	}

	public void setPreType(Short preType) {
		this.preType = preType;
	}
    
    public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getUnderLayer() {
		return underLayer;
	}

	public void setUnderLayer(String underLayer) {
		this.underLayer = underLayer;
	}

	public Integer getTotalStallNum() {
		return totalStallNum;
	}

	public void setTotalStallNum(Integer totalStallNum) {
		this.totalStallNum = totalStallNum;
	}
    
    public Integer getGridX() {
		return gridX;
	}

	public void setGridX(Integer gridX) {
		this.gridX = gridX;
	}

	public Integer getGridY() {
		return gridY;
	}

	public void setGridY(Integer gridY) {
		this.gridY = gridY;
	}

	public String getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(String businessTime) {
		this.businessTime = businessTime;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getRuntime() {
		return runtime;
	}

	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}

	public Integer getMonthRent() {
		return monthRent;
	}

	public void setMonthRent(Integer monthRent) {
		this.monthRent = monthRent;
	}

	public String getIncrease() {
		return increase;
	}

	public void setIncrease(String increase) {
		this.increase = increase;
	}

	public String getStrategyDescription() {
		return strategyDescription;
	}

	public void setStrategyDescription(String strategyDescription) {
		this.strategyDescription = strategyDescription;
	}

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

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
}
