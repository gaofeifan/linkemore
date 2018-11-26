package cn.linkmore.prefecture.controller.app.request;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("车区列表请求")
public class ReqPrefecture {
	
	@ApiModelProperty(value = "城市ID", required = false)
	private Long cityId;
	@ApiModelProperty(value = "经度", required = true) 
//	@Range(min=73, max=137,message="经度请确保在中国范围内")
	@NotBlank(message="经度不能为空") 
	private String longitude; 
	
	@ApiModelProperty(value = "纬度", required = true) 
//	@Range(min=3, max=54,message="纬度请确保在中国范围内")
	@NotBlank(message="纬度不能为空") 
	private String latitude;
	
	@ApiModelProperty(value = "城市标识", required = true) 
	private String cityFlag = "0";
	
	@ApiModelProperty(value = "车区名称（模糊搜索）cityFlag=0 表示所有车区搜索，车区名称默认为空", required = true) 
	private String preName;
	
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
	public String getCityFlag() {
		return cityFlag;
	}
	public void setCityFlag(String cityFlag) {
		this.cityFlag = cityFlag;
	}
	public String getPreName() {
		return preName;
	}
	public void setPreName(String preName) {
		this.preName = preName;
	}
}
