package cn.linkmore.prefecture.controller.app.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车区详情信息")
public class ResPrefectureDetail { 
	@ApiModelProperty(value = "主键")
	private Long id; 
	
	@ApiModelProperty(value = "车区名称")
	private String name;
	
	@ApiModelProperty(value = "空闲车位数量")
	private Integer leisureStall;
	
	@ApiModelProperty(value = "经度")
	private Double longitude;
	
	@ApiModelProperty(value = "纬度")
	private Double latitude;
	
	@ApiModelProperty(value = "车区地址")
	private String address;
	
	@ApiModelProperty(value = "免费时长")
	private String freeMins;
	
	@ApiModelProperty(value = "24小时封顶计费")
	private String topFee;
	
	@ApiModelProperty(value = "推荐车牌ID")
	private Long plateId;
	
	@ApiModelProperty(value = "推荐车牌号")
	private String plateNumber;
	
	/**
     * 营业时长8:00 - 20:00
     */
	@ApiModelProperty(value = "运营时长")
    private String businessTime;
    
	
	@ApiModelProperty(value = "车区分组列表")
	private List<ResPrefectureGroup> preGroupList; 
	
	public String getBusinessTime() {
		return businessTime;
	}

	public void setBusinessTime(String businessTime) {
		this.businessTime = businessTime;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLeisureStall() {
		return leisureStall;
	}

	public void setLeisureStall(Integer leisureStall) {
		this.leisureStall = leisureStall;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public List<ResPrefectureGroup> getPreGroupList() {
		return preGroupList;
	}

	public void setPreGroupList(List<ResPrefectureGroup> preGroupList) {
		this.preGroupList = preGroupList;
	}
	
}
