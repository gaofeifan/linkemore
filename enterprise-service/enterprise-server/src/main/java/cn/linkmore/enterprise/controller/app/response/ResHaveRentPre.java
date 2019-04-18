package cn.linkmore.enterprise.controller.app.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("长租车区信息")
public class ResHaveRentPre {

	@ApiModelProperty(value = "车区id")
	private Long preId;
	
	@ApiModelProperty(value = "车区名称")
	private String preName;
	
	@ApiModelProperty(value = "车区位置")
	private String address;
	
	@ApiModelProperty(value = "经度")
	private Double longitude;
	
	@ApiModelProperty(value = "纬度")
	private Double latitude;
	
	@ApiModelProperty(value = "距离")
	private String distance;

	@ApiModelProperty(value = "长租车位")
	private List<ResHaveRentPreStall> rentPreStalls = null;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public List<ResHaveRentPreStall> getRentPreStalls() {
		return rentPreStalls;
	}

	public void setRentPreStalls(List<ResHaveRentPreStall> rentPreStalls) {
		this.rentPreStalls = rentPreStalls;
	}
}
