package cn.linkmore.user.request;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

 
@ApiModel("城市定位请求")
public class ReqLocate {

	@ApiModelProperty(value = "经度", required = true) 
	@Range(min=73, max=137,message="经度请确保在中国范围内")
	@NotBlank(message="经度不能为空") 
	private String longitude;
	 
	@ApiModelProperty(value = "纬度", required = true) 
	@Range(min=3, max=54,message="纬度请确保在中国范围内")
	@NotBlank(message="纬度不能为空") 
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
