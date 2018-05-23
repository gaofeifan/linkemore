package cn.linkmore.user.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("城市信息")
public class ResCity {
	
	public final static int STATUS_CHECKED = 1;
	public final static int STATUS_UNCHECK = 0;
	public final static int STATUS_ASSIGN = 2;
	
	@ApiModelProperty(value = "主键")
	private Long id;
	
	@ApiModelProperty(value = "名称")
	private String name;
	
	@ApiModelProperty(value = "默认状态1定位选中，0未选中，2指定选中")
	private Integer status = 0; 
	
	@ApiModelProperty(value = "经度")
	private String longitude;
	
	@ApiModelProperty(value = "纬度")
	private String latitude;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
