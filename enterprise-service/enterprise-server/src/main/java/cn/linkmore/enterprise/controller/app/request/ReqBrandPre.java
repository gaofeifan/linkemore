package cn.linkmore.enterprise.controller.app.request;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("品牌车区列表请求")
public class ReqBrandPre {
	
	@ApiModelProperty(value = "城市ID", required = false)
	private Long cityId;
	@ApiModelProperty(value = "经度", required = true) 
	@Range(min=73, max=137,message="经度请确保在中国范围内")
	@NotBlank(message="经度不能为空") 
	private String longitude; 
	
	@ApiModelProperty(value = "纬度", required = true) 
	@Range(min=3, max=54,message="纬度请确保在中国范围内")
	@NotBlank(message="纬度不能为空") 
	private String latitude;
	
	@ApiModelProperty(value = "城市标识", required = true) 
	private Boolean cityFlag = false;
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
	public Boolean getCityFlag() {
		return cityFlag;
	}
	public void setCityFlag(Boolean cityFlag) {
		this.cityFlag = cityFlag;
	}
}
