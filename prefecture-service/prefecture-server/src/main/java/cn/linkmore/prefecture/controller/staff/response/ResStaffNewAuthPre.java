package cn.linkmore.prefecture.controller.staff.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("获取新车区权限数据")
public class ResStaffNewAuthPre {

	@ApiModelProperty(value = "车区id")
	private Long preId;
	
	@ApiModelProperty(value = "车区名称")
	private String preName;

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}
}
