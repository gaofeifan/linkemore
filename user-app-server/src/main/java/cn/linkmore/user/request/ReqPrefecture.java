package cn.linkmore.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("车区列表请求")
public class ReqPrefecture {
	@ApiModelProperty(value = "城市ID", required = true)
	private Long cityId;
	@ApiModelProperty(value = "经度", required = true)
	private String longitude; 
	@ApiModelProperty(value = "纬度", required = true)
	private String latitude;
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
}
