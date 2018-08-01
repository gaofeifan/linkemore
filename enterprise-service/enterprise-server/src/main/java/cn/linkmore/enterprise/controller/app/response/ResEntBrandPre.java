package cn.linkmore.enterprise.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("品牌车区信息")
public class ResEntBrandPre { 
	@ApiModelProperty(value = "主键")
	private Long id; 
	
	@ApiModelProperty(value = "品牌车区名称")
	private String name;
	
	@ApiModelProperty(value = "联系方式")
	private String contract;
	
	@ApiModelProperty(value = "车区名称")
	private String preName;
	
	@ApiModelProperty(value = "空闲车位数量")
	private Integer leisureStall;
	
	@ApiModelProperty(value = "凌猫车区空闲车位数量")
	private Integer linkmoreLeisureStall;
	
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

	@ApiModelProperty(value = "距离")
	private String distance;
	
	@ApiModelProperty(value = "推荐车牌ID")
	private Long plateId;
	
	@ApiModelProperty(value = "推荐车牌号")
	private String plateNumber;
	
	@ApiModelProperty(value = "企业id")
	private Long entId;
	
	@ApiModelProperty(value = "企业名称")
	private String entName;
	
	@ApiModelProperty(value = "专区id")
	private Long preId;
	
	@ApiModelProperty(value = "是否品牌授权用户")
	private Boolean brandUserFlag = false;

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

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
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

	public Long getEntId() {
		return entId;
	}

	public void setEntId(Long entId) {
		this.entId = entId;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public Boolean getBrandUserFlag() {
		return brandUserFlag;
	}

	public void setBrandUserFlag(Boolean brandUserFlag) {
		this.brandUserFlag = brandUserFlag;
	}

	public Integer getLinkmoreLeisureStall() {
		return linkmoreLeisureStall;
	}

	public void setLinkmoreLeisureStall(Integer linkmoreLeisureStall) {
		this.linkmoreLeisureStall = linkmoreLeisureStall;
	}
}
