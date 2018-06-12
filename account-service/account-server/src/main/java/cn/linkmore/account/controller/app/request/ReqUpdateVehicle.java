package cn.linkmore.account.controller.app.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("更新车牌号请求")
public class ReqUpdateVehicle {
	@ApiModelProperty(value="用户id",required=false)
	private Long userId;
	
	@ApiModelProperty(value="车牌型号",required=true)
	@NotNull(message="车辆型号不能为空")
	private Integer model;

	@ApiModelProperty(value="车牌品牌",required=true)
	@NotNull(message="车辆品牌不能为空")
	private Integer brandId;

	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	

}

