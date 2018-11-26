package cn.linkmore.prefecture.controller.staff.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车位锁详情数据")
public class ResStaffStallSn {

	@ApiModelProperty(value="车位id")
	private Long stallId;

	@ApiModelProperty(value="车位锁编号")
	private String stallSn;
	
	@ApiModelProperty(value="车位锁序列号")
	private String serialNumber;
	
	@ApiModelProperty(value="型号")
	private String model;
	
	@ApiModelProperty(value="版本")
	private String version;
	
	@ApiModelProperty(value="车位锁状态 1 升起  2 降下")
	private Integer stallLockStatus;

	@ApiModelProperty(value="车位锁离线状态 1离线  2 在线")
	private Integer lockOffLine = 1;
	
	@ApiModelProperty(value="超声波 0 无车 1 有车 2其他")
	private int ultrasonic;
	
	@ApiModelProperty(value="电池电量")
	private int battery;
	
	@ApiModelProperty(value="车位名称")
	private String stallName;
	
	@ApiModelProperty(value="车区名称")
	private String preName;

	@ApiModelProperty(value="车区Id")
	private Long preId;

	@ApiModelProperty(value="城市Id")
	private Long cityId;
	
	@ApiModelProperty(value="车位状态 状态:1，空闲；2，使用中；4，下线  ")
	private Short stallStatus;

	@ApiModelProperty(value=" 0 未安装  1已安装 ")
	private short installStatus = 0;
	
	@ApiModelProperty(value=" 是否绑定 true是 false 否 ")
	private boolean bindStatus = false;
	

	public String getStallSn() {
		return "0000"+serialNumber.toLowerCase();
	}

	public void setStallSn(String stallSn) {
		this.stallSn = stallSn;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getStallLockStatus() {
		return stallLockStatus;
	}

	public void setStallLockStatus(Integer stallLockStatus) {
		this.stallLockStatus = stallLockStatus;
	}

	public int getUltrasonic() {
		return ultrasonic;
	}

	public void setUltrasonic(int ultrasonic) {
		this.ultrasonic = ultrasonic;
	}

	public int getBattery() {
		return battery;
	}

	public void setBattery(int battery) {
		this.battery = battery;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public Short getStallStatus() {
		return stallStatus;
	}

	public void setStallStatus(Short stallStatus) {
		this.stallStatus = stallStatus;
	}

	public short getInstallStatus() {
		return installStatus;
	}

	public void setInstallStatus(short installStatus) {
		this.installStatus = installStatus;
	}

	public Integer getLockOffLine() {
		return lockOffLine;
	}

	public void setLockOffLine(Integer lockOffLine) {
		this.lockOffLine = lockOffLine;
	}

	public boolean isBindStatus() {
		return bindStatus;
	}

	public void setBindStatus(boolean bindStatus) {
		this.bindStatus = bindStatus;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}
}
