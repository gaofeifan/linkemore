package cn.linkmore.user.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车区地图")
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
	
	
}
