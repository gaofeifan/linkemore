package cn.linkmore.enterprise.controller.app.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class ResRentUserStall {

	@ApiModelProperty(value="车区名称")
	private String preName;
	
	@ApiModelProperty(value="车区Id")
	private Long preId;
	
	@ApiModelProperty(value="车位名称")
	private String stallName;
	
	@ApiModelProperty(value="车位id")
	private Long stallId;
	
	@ApiModelProperty(value="到期时间")
	private Date validity;
	
	@ApiModelProperty(value="电池电量")
	private int battery;
	
	@ApiModelProperty(value = "长租车位标识一对多标识 默认0 1开启")
	private Short rentOmType = 0;
	
	@ApiModelProperty(value = "长租车位标识多对一标识 默认0 1开启")
	private Short rentMoType = 0;
	/**
	 * 车位状态   0 上方无车 1 上方有车 ，其他值 表示未知
	 */ 
	@ApiModelProperty(value = "车位状态   0 上方无车 1 上方有车 ，其他值 表示未知")
	private int parkingState = 0;
	
	/**
	 *  异常电量(0 低于30 异常  1 大于30 正常 )
	 */ 
	@ApiModelProperty(value="异常电量(0 低于30 异常  1 大于30 正常 )")
	private int unusualBattery = 1;
	
	/**
	 * 上次使用时间 
	 */ 
	@ApiModelProperty(value="上次使用时间 ")
	private Date useUpLockTime;
	
	/**
	 * 降锁时间 
	 */ 
	@ApiModelProperty(value="降锁时间 ")
	private Date downLockTime;
	
	@ApiModelProperty(value="锁状态  1升起 2 降下")
	private int lockStatus;
	
	@ApiModelProperty(value="车位状态 :1，空闲；2，使用中；4，下线")
	private int stallStatus;
	
	/**
	 *  用户状态 0 授权人 1 被授权人
	 */ 
	@ApiModelProperty(value="用户状态 1 授权人展示车位使用记录 0 被授权人不展示车位使用记录 ")
	private int userStatus=0;
	
	/**
	 *  是否显示使用记录 0 显示 1不 显示
	 */ 
	@ApiModelProperty(value="是否显示使用记录 0 显示 1不 显示 ")
	private int isUserRecord=0;
	
	/**
	 *  当前是否有用户使用 0有 1无
	 */ 
	@ApiModelProperty(value="当前是否有用户使用 0无 1有 ")
	private int isUserUse=0;
	
	@ApiModelProperty(value="使用用户名称 ")
	private String UseUserName;
	
	@ApiModelProperty(value="使用用户电话")
	private String useUserMobile;

	@ApiModelProperty(value = "网关状态(默认展示0  调不到锁平台时显示0) 0离线 1 在线")
	private int gatewayStatus;
	
	@ApiModelProperty(value = "锁编码")
	private String lockSn;
	
	@ApiModelProperty(value = "地下几层")
	private String underLayer;
	
	@ApiModelProperty(value="当车位状态为使用中时  使用是否是被授权用户  0是授权用户 1被授权用户")
	private int isAuthUser = 0;
	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public Date getValidity() {
		return validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}

	public int getBattery() {
		return battery;
	}

	public void setBattery(int battery) {
		this.battery = battery;
	}

	public int getUnusualBattery() {
		if(battery <= 30) {
			unusualBattery = 0;
		}
		return unusualBattery;
	}

	public void setUnusualBattery(int unusualBattery) {
		this.unusualBattery = unusualBattery;
	}

	@JsonFormat(pattern="yy-MM-dd HH:mm",timezone="GMT+8")
	public Date getUseUpLockTime() {
		return useUpLockTime;
	}

	public void setUseUpLockTime(Date useUpLockTime) {
		this.useUpLockTime = useUpLockTime;
	}

	@JsonFormat(pattern="yy-MM-dd HH:mm",timezone="GMT+8")
	public Date getDownLockTime() {
		return downLockTime;
	}

	public void setDownLockTime(Date downLockTime) {
		this.downLockTime = downLockTime;
	}

	public int getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(int lockStatus) {
		this.lockStatus = lockStatus;
	}

	public int getStallStatus() {
		return stallStatus;
	}

	public void setStallStatus(int stallStatus) {
		this.stallStatus = stallStatus;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public int getIsUserRecord() {
		return isUserRecord;
	}

	public void setIsUserRecord(int isUserRecord) {
		this.isUserRecord = isUserRecord;
	}

	public int getIsUserUse() {
		return isUserUse;
	}

	public void setIsUserUse(int isUserUse) {
		this.isUserUse = isUserUse;
	}

	public String getUseUserName() {
		return UseUserName;
	}

	public void setUseUserName(String useUserName) {
		isUserUse = 1;
		UseUserName = useUserName;
	}

	public String getUseUserMobile() {
		return useUserMobile;
	}

	public void setUseUserMobile(String useUserMobile) {
		isUserUse = 1;
		this.useUserMobile = useUserMobile;
	}

	public int getParkingState() {
		return parkingState;
	}

	public void setParkingState(int parkingState) {
		this.parkingState = parkingState;
	}

	public Short getRentMoType() {
		return rentMoType;
	}

	public void setRentMoType(Short rentMoType) {
		this.rentMoType = rentMoType;
	}

	public Short getRentOmType() {
		return rentOmType;
	}

	public void setRentOmType(Short rentOmType) {
		this.rentOmType = rentOmType;
	}

	public int getGatewayStatus() {
		return gatewayStatus;
	}

	public void setGatewayStatus(int gatewayStatus) {
		this.gatewayStatus = gatewayStatus;
	}

	public String getUnderLayer() {
		return underLayer;
	}

	public void setUnderLayer(String underLayer) {
		this.underLayer = underLayer;
	}

	public int getIsAuthUser() {
		return isAuthUser;
	}

	public void setIsAuthUser(int isAuthUser) {
		this.isAuthUser = isAuthUser;
	}

	public String getLockSn() {
		return lockSn;
	}

	public void setLockSn(String lockSn) {
		this.lockSn = lockSn;
	}

	
}
