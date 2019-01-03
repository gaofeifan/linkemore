package cn.linkmore.prefecture.controller.staff.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("管理版车位详情")
public class ResStaffStallDetail {

	@ApiModelProperty("车位名称 (车位编号)")
	public String stallName;
	
	@ApiModelProperty("锁编号(序列号)")
	public String lockSn;
	
	@ApiModelProperty("锁状态(1:竖起状态 2：躺下 )")
	public Integer lockStatus;
	
	@ApiModelProperty(value="车位状态 状态:1，空闲；2，使用中；4，下线  ")
	private Integer status;
	/**
	 * 电量
	 */
	@ApiModelProperty("锁电量")
	private int betty;
	/**
	 * 车牌号
	 */
	@ApiModelProperty("车牌号")
	private String plate;
	/**
	 * 手机号
	 */
	@ApiModelProperty("手机号")
	private String mobile;
	@ApiModelProperty("车位id")
	private Long stallId;
	@ApiModelProperty("降锁时间")
	private Date downTime;
	@ApiModelProperty("进场时间")
	private Date approachTime;
	@ApiModelProperty("预约时间")
	private Date startTime;
	@ApiModelProperty("订单编号")
	private String orderNo;
	@ApiModelProperty("预约时长")
	private String startDate;
	@ApiModelProperty("异常原因")
	private String excName;
	@ApiModelProperty("异常原因Id")
	private Long excCode;
	@ApiModelProperty("复位状态  true可以复位 false不可以")
	private boolean resetStatus = true;
	@ApiModelProperty("上下线状态true上线 false下线")
	private boolean onoffStatus = false;
	@ApiModelProperty("故障原因Id")
	private Long faultId;
	@ApiModelProperty("地磁车状态 0无车  1有车  2其他")
	private Integer carStatus = 2;
	@ApiModelProperty("故障原因名称")
	private String faultName;
	@ApiModelProperty("订单类型")
	private String orderType;
	@ApiModelProperty(value = "是否被指定 0已经指定，1未指定")
	private int assignStatus = 1;
	@ApiModelProperty(value = "指定的车牌号")
	private String assignPlate;
	@ApiModelProperty(value = "订单状态 状态：1，未支付；2，已支付；3，已完成；4，已取消 5 退款，6挂起订单，7关闭订单")
	private Short orderStatus;
	@ApiModelProperty(value = "订单id")
	private Long orderId;
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
	public Integer getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}
	public int getBetty() {
		return betty;
	}
	public void setBetty(int betty) {
		this.betty = betty;
	}
	public String getPlate() {
		return plate;
	}
	public void setPlate(String plate) {
		this.plate = plate;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Long getStallId() {
		return stallId;
	}
	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")	
	public Date getDownTime() {
		return downTime;
	}

	public void setDownTime(Date downTime) {
		this.downTime = downTime;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")	
	public Date getApproachTime() {
		return approachTime;
	}

	public void setApproachTime(Date approachTime) {
		this.approachTime = approachTime;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone="GMT+8")	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getExcName() {
		return excName;
	}
	public void setExcName(String excName) {
		this.excName = excName;
	}
	public Long getExcCode() {
		return excCode;
	}
	public void setExcCode(Long excCode) {
		this.excCode = excCode;
	}
	public boolean isResetStatus() {
		return resetStatus;
	}
	public void setResetStatus(boolean resetStatus) {
		this.resetStatus = resetStatus;
	}
	public boolean isOnoffStatus() {
		return onoffStatus;
	}
	public void setOnoffStatus(boolean onoffStatus) {
		this.onoffStatus = onoffStatus;
	}
	public Long getFaultId() {
		return faultId;
	}
	public void setFaultId(Long faultId) {
		this.faultId = faultId;
	}
	public Integer getCarStatus() {
		return carStatus;
	}
	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
	}
	public String getFaultName() {
		return faultName;
	}
	public void setFaultName(String faultName) {
		this.faultName = faultName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public int getAssignStatus() {
		return assignStatus;
	}
	public void setAssignStatus(int assignStatus) {
		this.assignStatus = assignStatus;
	}
	public String getAssignPlate() {
		return assignPlate;
	}
	public void setAssignPlate(String assignPlate) {
		this.assignPlate = assignPlate;
	}
	public Short getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Short orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
