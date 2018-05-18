package cn.linkmore.user.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("城市定位请求")
public class ReqLocate {

	@ApiModelProperty(value = "经度", required = true)
	private String longitude;
	
	@ApiModelProperty(value = "纬度", required = true)
	private String latitude;

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
