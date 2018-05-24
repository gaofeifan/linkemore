package cn.linkmore.user.request;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("修改车牌号")
public class ReqVehicleMark {
	
	@ApiModelProperty(value="车牌号",required=true)
	@NotBlank(message="车牌号不能为空")
	private String vehMark;

	@ApiModelProperty(value="用户id",required=false)
	private Long userId;
	
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

