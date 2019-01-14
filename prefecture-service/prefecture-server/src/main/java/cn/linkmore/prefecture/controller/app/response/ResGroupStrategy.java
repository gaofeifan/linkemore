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
	
	@ApiModelProperty(value = "推荐车牌ID")
	private Long plateId;
	
	@ApiModelProperty(value = "推荐车牌号")
	private String plateNumber;
	
	@ApiModelProperty(value = "车区网格横格数量")
	private Integer gridX;
	
	@ApiModelProperty(value = "车区网格纵格数量")
	private Integer gridY;
	
	@ApiModelProperty(value = "车场数据Map其中status=1表示空闲 2表示占用 4表示下线")
	private List<Map<String,Object>> parkingDataMap;
	
	@ApiModelProperty(value = "分组下所有车位列表")
	private List<ResStall> stallList;
	
	@ApiModelProperty(value = "计价规则List数组")
	private List<String> descList;
	
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

	public List<Map<String, Object>> getParkingDataMap() {
		return parkingDataMap;
	}

	public void setParkingDataMap(List<Map<String, Object>> parkingDataMap) {
		this.parkingDataMap = parkingDataMap;
	}

	public Long getPlateId() {
		return plateId;
	}

	public void setPlateId(Long plateId) {
		this.plateId = plateId;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public Integer getGridX() {
		return gridX;
	}

	public void setGridX(Integer gridX) {
		this.gridX = gridX;
	}

	public Integer getGridY() {
		return gridY;
	}

	public void setGridY(Integer gridY) {
		this.gridY = gridY;
	}

	public List<String> getDescList() {
		return descList;
	}

	public void setDescList(List<String> descList) {
		this.descList = descList;
	}

	public List<ResStall> getStallList() {
		return stallList;
	}

	public void setStallList(List<ResStall> stallList) {
		this.stallList = stallList;
	}
	
 }
