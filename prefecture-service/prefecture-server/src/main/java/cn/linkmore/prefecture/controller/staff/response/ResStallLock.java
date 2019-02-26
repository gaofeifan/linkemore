package cn.linkmore.prefecture.controller.staff.response;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车位锁信息")
public class ResStallLock {
	@ApiModelProperty(value="车位锁编号")
	private String lockSn;
	
	@ApiModelProperty(value="信号")
	private String signal;
	
	@ApiModelProperty(value="车位名称")
	private String stallName;
	
	@ApiModelProperty(value="电池电量")
	private String battery;
	
	@ApiModelProperty(value="车位id")
	private Long stallId;
	
	@ApiModelProperty(value="绑定状态 0未绑定 1已绑定")
	private String bindFlag = "0";

	public String getLockSn() {
		/*if(StringUtils.isNoneBlank(lockSn)) {
			if(lockSn.contains("0000")) {
				return lockSn.substring(4);
			}
		}*/
		return lockSn;
	}

	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}

	public String getSignal() {
		return signal;
	}

	public void setSignal(String signal) {
		this.signal = signal;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}

	public String getBattery() {
		return battery;
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public String getBindFlag() {
		return bindFlag;
	}

	public void setBindFlag(String bindFlag) {
		this.bindFlag = bindFlag;
	}
	
}
