package cn.linkmore.enterprise.controller.staff.request;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("根据编码操作车位车锁")
public class SraffReqConStallSn {

	@ApiModelProperty("锁编码")
	@NotNull(message="锁编码能为空")
	private String lockSn;
	
	@ApiModelProperty("操作动作( 1:下降 2 升起 )")
	@NotNull(message="状态不能为空")
	@Range(max=2,min=1,message="操作动作为1或2")
	private Integer state;
	

	

	public String getLockSn() {
		return lockSn;
	}

	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}
}
