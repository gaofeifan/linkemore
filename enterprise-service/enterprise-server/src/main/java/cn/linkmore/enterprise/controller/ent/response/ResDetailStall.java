/**
 * 
 */
package cn.linkmore.enterprise.controller.ent.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author luzhishen
 * @Date 2018年7月21日
 * @Version v1.0
 */
@ApiModel("锁信息")
public class ResDetailStall {

	public static Long DOWN_STATUS = 4L;
	
	
	/**
	 * 锁编号
	 */
	@ApiModelProperty("锁编号")
	private String slaveCode;

	/**
	 * 锁升降状态
	 */
	@ApiModelProperty("锁状态(1:竖起状态 0：躺下)")
	private int status;

	/**
	 * 电量
	 */
	@ApiModelProperty("锁电量")
	private int betty;

	/**
	 * 车牌号
	 */
	@ApiModelProperty("车牌号 为固定车位时使用")
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
	
	@ApiModelProperty("故障原因名称")
	private String faultName;
	
	/**
	 *  超声波设备状态   0 异常 1正常 其他值表示未知
	 */ 
	private int inductionState;
	
	/**
	 * 车位状态 状态:1，空闲；2，使用中；3,预下线；4，下线  5 故障
	 */
	@ApiModelProperty(value="车位状态 状态:1，空闲；2，使用中；3,预下线；4，下线  5 故障")
	private Integer stallStatus;
	
	
	public boolean getOnoffStatus() {
		return onoffStatus;
	}

	public void setOnoffStatus(boolean onoffStatus) {
		this.onoffStatus = onoffStatus;
	}

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

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public boolean isResetStatus() {
		return resetStatus;
	}

	public void setResetStatus(boolean resetStatus) {
		this.resetStatus = resetStatus;
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

	public Long getFaultId() {
		return faultId;
	}

	public void setFaultId(Long faultId) {
		this.faultId = faultId;
	}

	public String getFaultName() {
		return faultName;
	}

	public void setFaultName(String faultName) {
		this.faultName = faultName;
	}

	public int getInductionState() {
		return inductionState;
	}

	public void setInductionState(int inductionState) {
		this.inductionState = inductionState;
	}

	public Integer getStallStatus() {
		return stallStatus;
	}

	public void setStallStatus(Integer stallStatus) {
		this.stallStatus = stallStatus;
	}
}
