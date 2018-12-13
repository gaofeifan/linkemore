package cn.linkmore.prefecture.controller.staff.request;

import javax.validation.constraints.NotNull;

import cn.linkmore.annotation.Digits;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("查询车区车位列表")
public class ReqStaffStallList {

	@ApiModelProperty(value="车区id必填",required=true)
	@NotNull(message="车区id不能为空")
	@Digits(message="车区id为整数")
	private Long preId;
	
	@ApiModelProperty(value="车位名称模糊查询(非必填 )",required=false)
	private String stallName;
	
	@ApiModelProperty(value="车位类型状态(预留字段 非必填) 0 空闲 1使用 2故障",required=false)
	private Integer status;

	@ApiModelProperty(value="车位类型(0自营/临停,2固定,3VIP（预留类型）)",required=true)
	private Integer type;

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
