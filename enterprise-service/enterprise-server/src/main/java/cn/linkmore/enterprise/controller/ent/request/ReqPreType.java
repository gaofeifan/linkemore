package cn.linkmore.enterprise.controller.ent.request;

import javax.validation.constraints.NotNull;

import cn.linkmore.annotation.Digits;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("查询车区运营")
public class ReqPreType {

	@ApiModelProperty(required=true,value = "车区id")
	@NotNull(message="参数不能为空")
//	@Digits(message="参数必须为整数")
	private Long preId;
	
	@ApiModelProperty(required=true,value = "类型 0 7天 1 15天 2 30天")
//	@Digits(message="类型数据应为1-3",regex="[1,2,3]")
	@NotNull(message="类型不能为空")
	private Short type;

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}
	
	
	
	
}
