package cn.linkmore.enterprise.controller.staff.request;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("指定车位Bean")
public class AssignStallRequestBean implements Serializable {

	private static final long serialVersionUID = 592015174163099310L;

	@ApiModelProperty(value = "车位锁Sn码", required = false)
	private String lockSn;

	@ApiModelProperty(value = "车牌号", required = false)
	private String plate;
	
	@ApiModelProperty(value = "专区ID", required = false)
	private String preId;

	public String getLockSn() {
		return lockSn;
	}

	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getPreId() {
		return preId;
	}

	public void setPreId(String preId) {
		this.preId = preId;
	}
	
	

}
