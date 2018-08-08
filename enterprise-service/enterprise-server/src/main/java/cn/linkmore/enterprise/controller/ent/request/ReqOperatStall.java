/**
 * 
 */
package cn.linkmore.enterprise.controller.ent.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

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
	@NotNull(message="车位不能为空")
	private Long stallId;
	
	@ApiModelProperty("操作动作(1:下降 2 升起)")
	@NotNull(message="状态不能为空")
	@Range(max=2,min=1,message="操作动作为1或2")
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
