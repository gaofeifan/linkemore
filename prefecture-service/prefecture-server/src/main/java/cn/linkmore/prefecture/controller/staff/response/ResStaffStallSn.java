package cn.linkmore.prefecture.controller.staff.response;

import java.util.ArrayList;
import java.util.List;

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
	
	@ApiModelProperty("分区")
	private String  areaName;
	
	@ApiModelProperty(value="车位锁状态 1 升起  2 降下 3 未知")
	private Integer stallLockStatus;

	@ApiModelProperty(value="车位锁离线状态 1离线  2 在线")
	private Integer lockOffLine = 1;
	
	@ApiModelProperty(value="超声波 0 无车 1 有车 2其他(未知)")
	private int ultrasonic;
	
	@ApiModelProperty(value="超声波设备状态 0 异常 1正常 其他值表示未知")
	private int inductionState;
	
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

	@ApiModelProperty(value="城市名称")
	private String cityName;
	
	@ApiModelProperty(value="车位状态 状态:1，空闲；2，使用中；4，下线  ")
	private Short stallStatus;

	@ApiModelProperty(value=" 0 未安装  1已安装 ")
	private short installStatus = 0;
	
	@ApiModelProperty(value=" 是否绑定 true是 false 否 ")
	private boolean bindStatus = false;
	
	@ApiModelProperty(value="1 未绑定 2已绑定")
	private int bindStata = 1;
	@ApiModelProperty(value="是否具有删除权限")
	private short gatewayDelete = 0;
	@ApiModelProperty(value="车位锁绑定的网关")
	private List<ResLockGatewayList> gatewayList = new ArrayList<>();
	public String getStallSn() {
		return stallSn;
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
		if(stallSn != null) {
			return "0000"+stallSn.toLowerCase();
		}
		return null;
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

	public int getInductionState() {
		return inductionState;
	}

	public void setInductionState(int inductionState) {
		this.inductionState = inductionState;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getBindStata() {
		return bindStata;
	}

	public void setBindStata(int bindStata) {
		this.bindStata = bindStata;
	}

	public List<ResLockGatewayList> getGatewayList() {
		return gatewayList;
	}

	public void setGatewayList(List<ResLockGatewayList> gatewayList) {
		this.gatewayList = gatewayList;
	}

	public short getGatewayDelete() {
		return gatewayDelete;
	}

	public void setGatewayDelete(short gatewayDelete) {
		this.gatewayDelete = gatewayDelete;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
