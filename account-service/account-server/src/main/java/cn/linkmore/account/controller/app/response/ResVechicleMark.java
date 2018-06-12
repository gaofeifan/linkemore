package cn.linkmore.account.controller.app.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("车牌号管理")
public class ResVechicleMark {
	@ApiModelProperty(value="id")
	private Long id;
	
	@ApiModelProperty(value="用户id")
	private Long userId;

	@ApiModelProperty(value="车牌号")
	private String vehMark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVehMark() {
		return vehMark;
	}

	public void setVehMark(String vehMark) {
		this.vehMark = vehMark;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}

