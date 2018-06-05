package cn.linkmore.ops.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("城市请求bean")
public class ReqCity {
	@ApiModelProperty(value = "主键", required = false)
	private Long id;

	@ApiModelProperty(value = "城市名称", required = true)
	private String cityName;

	@ApiModelProperty(value = "城市代码", required = true)
	private String adcode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAdcode() {
		return adcode;
	}

	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}

}
