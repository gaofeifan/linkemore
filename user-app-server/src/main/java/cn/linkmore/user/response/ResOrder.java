package cn.linkmore.user.response;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("订单信息")
public class ResOrder {
	@ApiModelProperty(value = "订单ID")
	private Long id;
	@ApiModelProperty(value = "车位ID")
	private Long stallId;
	@ApiModelProperty(value = "车区ID")
	private Long prefectureId;
	@ApiModelProperty(value = "车位名称")
	private String stallName;
	@ApiModelProperty(value = "车区名称")
	private String prefectureName;
	@ApiModelProperty(value = "车区地址")
	private String prefectureAddress;
	@ApiModelProperty(value = "车牌号")
	private String plateNumber;
	@ApiModelProperty(value = "预约时间")
	private Date createTime;
	@ApiModelProperty(value = "停车时长")
	private Integer parkingTime;
	@ApiModelProperty(value = "停车费用")
	private String totalAmount;
	@ApiModelProperty(value = "订单状态[1预约中,3已结账,6已挂起]")
	private Short status; 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getStallId() {
		return stallId;
	}
	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}
	public Long getPrefectureId() {
		return prefectureId;
	}
	public void setPrefectureId(Long prefectureId) {
		this.prefectureId = prefectureId;
	}
	public String getStallName() {
		return stallName;
	}
	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
	public String getPrefectureName() {
		return prefectureName;
	}
	public void setPrefectureName(String prefectureName) {
		this.prefectureName = prefectureName;
	}
	public String getPrefectureAddress() {
		return prefectureAddress;
	}
	public void setPrefectureAddress(String prefectureAddress) {
		this.prefectureAddress = prefectureAddress;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getParkingTime() {
		return parkingTime;
	}
	public void setParkingTime(Integer parkingTime) {
		this.parkingTime = parkingTime;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}  
}
