package cn.linkmore.user.response;

import java.util.Date;

import cn.linkmore.order.response.ResUserOrder;
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
	private Date startTime;
	@ApiModelProperty(value = "停车时长[分钟]")
	private Integer parkingTime;
	@ApiModelProperty(value = "结束时间")
	private Date endTime;
	@ApiModelProperty(value = "停车费用")
	private String totalAmount;
	@ApiModelProperty(value = "支付类型")
	private Short payType;
	@ApiModelProperty(value = "实际费用")
	private String actualAmount; 
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Short getPayType() {
		return payType;
	}
	public void setPayType(Short payType) {
		this.payType = payType;
	}
	public String getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(String actualAmount) {
		this.actualAmount = actualAmount;
	}
	public void copy(ResUserOrder ruo) {
		this.setStartTime(ruo.getCreateTime());
		this.setId(ruo.getId());
		Date start = ruo.getCreateTime();
		Date end = new Date(); 
		if(ruo.getStatusHistory()!=null) {
			end = ruo.getStatusTime(); 
		}
		this.setEndTime(end);
		this.setParkingTime(new Long((end.getTime()-start.getTime())/(60*1000)).intValue());
		this.setPlateNumber(ruo.getPlateNo());
		this.setPrefectureId(ruo.getPreId());
		this.setStallId(ruo.getStallId());
		this.setStatus(ruo.getStatus().shortValue());
		this.setStallName(ruo.getStallName());
		this.setPrefectureName(ruo.getPreName());
		
	}
}
