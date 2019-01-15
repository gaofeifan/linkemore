package cn.linkmore.prefecture.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("地图车区信息")
public class ResPrefecture { 
	@ApiModelProperty(value = "主键")
	private Long id; 
	
	@ApiModelProperty(value = "车区名称")
	private String name;
	
	@ApiModelProperty(value = "空闲车位数量")
	private Integer leisureStall;
	
	@ApiModelProperty(value = "经度")
	private Double longitude;
	
	@ApiModelProperty(value = "纬度")
	private Double latitude;
	
	@ApiModelProperty(value = "车区地址")
	private String address;
	
	@ApiModelProperty(value = "城市id")
	private Long cityId;
	
	@ApiModelProperty(value = "计费时间")
	private String chargeTime;
	
	@ApiModelProperty(value = "计费价格")
	private String chargePrice;

	@ApiModelProperty(value = "车区地图")
	private String imageUrl;

	@ApiModelProperty(value = "专区类型(0普通，1奥迪内部定制专区")
	private Integer type;
	
	@ApiModelProperty(value = "是否受限")
	private short limitStatus;
	
	@ApiModelProperty(value = "距离")
	private String distance;
	
	@ApiModelProperty(value = "推荐车牌ID")
	private Long plateId;
	
	@ApiModelProperty(value = "推荐车牌号")
	private String plateNumber;
	
	@ApiModelProperty(value = "营业时长")
	private String businessTime;
	
	@ApiModelProperty(value = "区域地面、地下")
    private String region;
	
	@ApiModelProperty(value = "区域地面")
    private String region1;
	
	@ApiModelProperty(value = "区域地下")
    private String region2;
	
	@ApiModelProperty(value = "地下层级B2-B5")
    private String underLayer;

	@ApiModelProperty(value = "总车位数")
    private Integer totalStallNum;
	
	@ApiModelProperty(value = "车区类型，1临停车区（包含临停和固定车位） 2固定车区（全部为固定车位）")
    private Short preType = (short)1;
    
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

	public String getRegion1() {
		return region1;
	}

	public void setRegion1(String region1) {
		this.region1 = region1;
	}

	public String getRegion2() {
		return region2;
	}

	public void setRegion2(String region2) {
		this.region2 = region2;
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
	
	public String getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(String businessTime) {
		this.businessTime = businessTime;
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
		this.name = name;
	}

	public Integer getLeisureStall() {
		return leisureStall;
	}

	public void setLeisureStall(Integer leisureStall) {
		this.leisureStall = leisureStall;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public short getLimitStatus() {
		return limitStatus;
	}

	public void setLimitStatus(short limitStatus) {
		this.limitStatus = limitStatus;
	}

	public String getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(String chargeTime) {
		this.chargeTime = chargeTime;
	}

	public String getChargePrice() {
		return chargePrice;
	}

	public void setChargePrice(String chargePrice) {
		this.chargePrice = chargePrice;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public Long getPlateId() {
		return plateId;
	}

	public void setPlateId(Long plateId) {
		this.plateId = plateId;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	} 
	
}
