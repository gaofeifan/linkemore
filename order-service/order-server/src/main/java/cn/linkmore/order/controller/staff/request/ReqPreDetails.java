package cn.linkmore.order.controller.staff.request;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车场详情")
public class ReqPreDetails {

	@ApiModelProperty("车场id")
	@NotNull(message="车场id不能为空")
	private Long preId;
	
	@ApiModelProperty(value="停车场层数",required=false)
	private String floor;

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	
	
}
