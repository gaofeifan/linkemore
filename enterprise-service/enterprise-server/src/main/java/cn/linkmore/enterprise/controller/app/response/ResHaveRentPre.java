package cn.linkmore.enterprise.controller.app.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("长租车区信息-最新版")
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
	
	@ApiModelProperty(value = "车位导航图")
	private String guideImage;
	
	@ApiModelProperty(value = "车位导航说明")
	private String guideRemark;
	
	@ApiModelProperty(value = "路径规划标识默认为0禁用 展示车位在哪、启用 为1展示最新的路径规划")
	private Short pathFlag = 0;

	@ApiModelProperty(value = "长租车位列表")
	private List<ResHaveRentPreStall> rentPreStalls = null;

	public Short getPathFlag() {
		return pathFlag;
	}

	public void setPathFlag(Short pathFlag) {
		this.pathFlag = pathFlag;
	}

	public String getGuideImage() {
		return guideImage;
	}

	public void setGuideImage(String guideImage) {
		this.guideImage = guideImage;
	}

	public String getGuideRemark() {
		return guideRemark;
	}

	public void setGuideRemark(String guideRemark) {
		this.guideRemark = guideRemark;
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
