package cn.linkmore.enterprise.controller.staff.request;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车位操作请求封装Bean")
public class StallOperateRequestBean  implements Serializable{
	
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1319498234254480661L;

	@ApiModelProperty(value = "车位ID", required = true)
	private Long stallId;
	
	@ApiModelProperty(value = "分类", required = true)
	private Short type;
	
	@ApiModelProperty(value = "原因ID", required = true)
	private Long remarkId;
	
	@ApiModelProperty(value = "原因文字", required = true)
	private String remark;
	
	public Long getStallId() {
		return stallId;
	}
	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public Long getRemarkId() {
		return remarkId;
	}
	public void setRemarkId(Long remarkId) {
		this.remarkId = remarkId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
