package cn.linkmore.user.response;

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
	
	@ApiModelProperty(value = "首小时价格")
	private String firstHour;
	
	@ApiModelProperty(value = "时间基数 15分钟等")
	private String timelyLong;

	@ApiModelProperty(value = "时间基数单位")
	private String timelyUnit;

	@ApiModelProperty(value = "免费时长")
	private Long freeMins;
	
	@ApiModelProperty(value = "专区类型(0普通，1奥迪内部定制专区")
	private Integer type;
	
	@ApiModelProperty(value = "是否受限")
	private short limitStatus;

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

	public String getFirstHour() {
		return firstHour;
	}

	public void setFirstHour(String firstHour) {
		this.firstHour = firstHour;
	}

	public String getTimelyLong() {
		return timelyLong;
	}

	public void setTimelyLong(String timelyLong) {
		this.timelyLong = timelyLong;
	}

	public String getTimelyUnit() {
		return timelyUnit;
	}

	public void setTimelyUnit(String timelyUnit) {
		this.timelyUnit = timelyUnit;
	}

	public Long getFreeMins() {
		return freeMins;
	}

	public void setFreeMins(Long freeMins) {
		this.freeMins = freeMins;
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
	
}
