package cn.linkmore.prefecture.controller.staff.request;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("地锁安装")
public class ReqLockIntall {

	@ApiModelProperty("车区")
	 Long preId;
	@ApiModelProperty("分区")
	String  areaName;
	@ApiModelProperty("车位号")
	@NotBlank(message="车位号不能为空")
	String stallName;
	@ApiModelProperty("序列号")
	@NotBlank(message="车位锁编号不能为空")
	String lockSn;

	public Long getPreId() {
		return preId;
	}
	public void setPreId(Long preId) {
		this.preId = preId;
	}
	

	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getLockSn() {
		if(lockSn != null) {
			if(lockSn.contains("0000")) {
				lockSn = lockSn.substring(4);
			}
			lockSn = lockSn.toUpperCase();
		}
		return lockSn;
	}
	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}
	public String getStallName() {
		return stallName;
	}
	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
	
	
	
	
}
