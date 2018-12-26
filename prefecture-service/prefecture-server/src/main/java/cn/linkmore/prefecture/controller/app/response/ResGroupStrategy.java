package cn.linkmore.prefecture.controller.app.response;

import java.util.List;
import java.util.Map;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车区分组计费详情")
public class ResGroupStrategy { 
	@ApiModelProperty(value = "车区主键")
	private Long preId; 
	
	@ApiModelProperty(value = "车区分组主键")
	private Long groupId; 
	
	@ApiModelProperty(value = "车区分组名称")
	private String groupName;
	
	@ApiModelProperty(value = "免费时长")
	private String freeMins;
	
	@ApiModelProperty(value = "24小时封顶计费")
	private String topFee;
	
	@ApiModelProperty(value = "运营时长")
    private String businessTime;
	
	@ApiModelProperty(value = "计价规则")
	private String desc;
	
	@ApiModelProperty(value = "当前时间段")
	private String currentTimePeriod;
	
	@ApiModelProperty(value = "当前计费")
	private String currentFee;
	
	@ApiModelProperty(value = "车场数据")
	private String parkingData;
	
	@ApiModelProperty(value = "车位列表")
	private List<ResStall> stalls;
	
	@ApiModelProperty(value = "车场数据Map")
	private List<Map<String,Object>> parkingDataMap;
	
	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getFreeMins() {
		return freeMins;
	}

	public void setFreeMins(String freeMins) {
		this.freeMins = freeMins;
	}

	public String getTopFee() {
		return topFee;
	}

	public void setTopFee(String topFee) {
		this.topFee = topFee;
	}

	public String getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(String businessTime) {
		this.businessTime = businessTime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCurrentTimePeriod() {
		return currentTimePeriod;
	}

	public void setCurrentTimePeriod(String currentTimePeriod) {
		this.currentTimePeriod = currentTimePeriod;
	}

	public String getCurrentFee() {
		return currentFee;
	}

	public void setCurrentFee(String currentFee) {
		this.currentFee = currentFee;
	}

	public String getParkingData() {
		return parkingData;
	}

	public void setParkingData(String parkingData) {
		this.parkingData = parkingData;
	}

	public List<ResStall> getStalls() {
		return stalls;
	}

	public void setStalls(List<ResStall> stalls) {
		this.stalls = stalls;
	}

	public List<Map<String, Object>> getParkingDataMap() {
		return parkingDataMap;
	}

	public void setParkingDataMap(List<Map<String, Object>> parkingDataMap) {
		this.parkingDataMap = parkingDataMap;
	}
	
 }
