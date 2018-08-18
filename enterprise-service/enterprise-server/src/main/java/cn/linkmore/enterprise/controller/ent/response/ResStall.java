package cn.linkmore.enterprise.controller.ent.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车位信息")
public class ResStall {

	/**
	 *  车位id
	 */ 
	@ApiModelProperty(value="车位id")
	private Long stallId;
	/**
	 *  车位名称
	 */ 
	@ApiModelProperty(value="车位名称")
	private String stallName;
	/**
	 * 车位锁编号
	 */
	@ApiModelProperty(value="车位锁编号")
	private String lockSn;
	/**
	 * 车位状态 状态:1，空闲；2，使用中；3,预下线；4，下线  5 故障
	 */
	@ApiModelProperty(value="车位状态 状态:1，空闲；2，使用中；3,预下线；4，下线  5 故障")
	private Integer status;
	/**
	 * 分类 0自营，1临停，2长租，3VIP
	 */
	@ApiModelProperty(value="分类 0自营，1临停，2长租，3VIP")
	private short type;
	/**
	 * 车牌号
	 */
	@ApiModelProperty(value="车牌号")
	private String plateNo;
	/**
	 * 车区id
	 */
	@ApiModelProperty(value="车区id")
	private Long preId;
	
	@ApiModelProperty(value="车区id")
	private String pId;
	
	/**
	 *  车位锁异常状态  true 正常车位  false 异常车位
	 */ 
	@ApiModelProperty(value="车位锁异常状态  true 正常车位  false 异常车位")
	private boolean excStatus = true;
	
	@ApiModelProperty(value="锁状态 1，升起；2，降下")
	private Integer lockStatus;

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}

	public String getLockSn() {
		return lockSn;
	}

	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public boolean isExcStatus() {
		return excStatus;
	}

	public void setExcStatus(boolean excStatus) {
		this.excStatus = excStatus;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public Integer getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}
}
