package cn.linkmore.prefecture.controller.app.request;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("车区列表请求")
public class ReqNearPrefecture {
	
	@ApiModelProperty(value = "搜索位置经度", required = true) 
//	@Range(min=73, max=137,message="经度请确保在中国范围内")
	@NotBlank(message="搜索位置经度不能为空") 
	private String searchLongitude; 
	
	@ApiModelProperty(value = "搜索位置纬度", required = true) 
//	@Range(min=3, max=54,message="纬度请确保在中国范围内")
	@NotBlank(message="搜索位置纬度不能为空") 
	private String searchLatitude;
	
	@ApiModelProperty(value = "当前经度", required = true) 
//	@Range(min=73, max=137,message="经度请确保在中国范围内")
	@NotBlank(message="当前位置经度不能为空") 
	private String currentLongitude; 
	
	@ApiModelProperty(value = "当前纬度", required = true) 
//	@Range(min=3, max=54,message="纬度请确保在中国范围内")
	@NotBlank(message="当前位置纬度不能为空") 
	private String currentLatitude;

	public String getSearchLongitude() {
		return searchLongitude;
	}

	public void setSearchLongitude(String searchLongitude) {
		this.searchLongitude = searchLongitude;
	}

	public String getSearchLatitude() {
		return searchLatitude;
	}

	public void setSearchLatitude(String searchLatitude) {
		this.searchLatitude = searchLatitude;
	}

	public String getCurrentLongitude() {
		return currentLongitude;
	}

	public void setCurrentLongitude(String currentLongitude) {
		this.currentLongitude = currentLongitude;
	}

	public String getCurrentLatitude() {
		return currentLatitude;
	}

	public void setCurrentLatitude(String currentLatitude) {
		this.currentLatitude = currentLatitude;
	}
}
