/**
 * 
 */
package cn.linkmore.enterprise.controller.ent.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author luzhishen
 * @Date 2018年7月21日
 * @Version v1.0
 */
@ApiModel("操作车位")
public class ReqOperatStall {
	
	@ApiModelProperty("车位id")
	private Long stallId;
	
	@ApiModelProperty("操作动作(1:下降 2 升起)")
	private Integer state;

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
