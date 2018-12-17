package cn.linkmore.prefecture.controller.staff.response;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("新权限城市数据")
public class ResStaffNewAuth {

	@ApiModelProperty("城市id")
	private Long cityId;
	
	@ApiModelProperty("城市名称")
	private String cityName;
	
	private List<ResStaffNewAuthPre> pres = new ArrayList<>();

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<ResStaffNewAuthPre> getPres() {
		return pres;
	}

	public void setPres(List<ResStaffNewAuthPre> pres) {
		this.pres = pres;
	}
}
