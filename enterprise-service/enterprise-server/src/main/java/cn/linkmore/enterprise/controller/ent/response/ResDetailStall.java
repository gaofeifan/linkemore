/**
 * 
 */
package cn.linkmore.enterprise.controller.ent.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author luzhishen
 * @Date 2018年7月21日
 * @Version v1.0
 */
@ApiModel("锁信息")
public class ResDetailStall {
	
	/**
	 * 锁编号
	 */
	@ApiModelProperty("锁编号")
	private String slaveCode;
	
	/**
	 * 锁升降状态
	 */
	@ApiModelProperty("锁状态(1:升起 2：降下)")
	private int status;
	
	/**
	 * 电量
	 */
	@ApiModelProperty("锁电量")
	private int betty;

	public String getSlaveCode() {
		return slaveCode;
	}

	public void setSlaveCode(String slaveCode) {
		this.slaveCode = slaveCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBetty() {
		return betty;
	}

	public void setBetty(int betty) {
		this.betty = betty;
	}

}
