package cn.linkmore.order.controller.app.response;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.linkmore.order.response.ResUserOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("订单详情")
public class ResOrderDetail{
	@ApiModelProperty(value = "主键")
	private Long id;
	@ApiModelProperty(value = "编号")
	private String orderNo;
	@ApiModelProperty(value = "专区名")
	private String prefectureName;
	@ApiModelProperty(value = "车牌号")
	private String plateNumber;
	@ApiModelProperty(value = "车位名")
	private String stallName;
	@ApiModelProperty(value = "状态")
	private Short status;
	@ApiModelProperty(value = "预约时间")
	private Date orderTime;
	@ApiModelProperty(value = "开始时间")
	private Date startTime;
	@ApiModelProperty(value = "结束时间")
	private Date endTime;
	@ApiModelProperty(value = "支付时间")
	private Date payTime;
	@ApiModelProperty(value = "支付类型[1免费，2停车券，3账户，4支付宝，5微信，6银联]")
	private Short payType;
	@ApiModelProperty(value = "总金额")
	private BigDecimal totalAmount;
	@ApiModelProperty(value = "停车券金额")
	private BigDecimal couponAmount;
	@ApiModelProperty(value = "支付金额")
	private BigDecimal actualAmount;
	@ApiModelProperty(value = "停车时长")
	private String parkingTime;
	@ApiModelProperty(value = "离开免费时长")
	private Integer leaveTime;
	 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPrefectureName() {
		return prefectureName;
	}
	public void setPrefectureName(String prefectureName) {
		this.prefectureName = prefectureName;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getStallName() {
		return stallName;
	}
	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
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
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public Short getPayType() {
		return payType;
	}
	public void setPayType(Short payType) {
		this.payType = payType;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(BigDecimal couponAmount) {
		this.couponAmount = couponAmount;
	}
	public BigDecimal getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}
	
	
	public String getParkingTime() {
		return parkingTime;
	}
	public void setParkingTime(String parkingTime) {
		this.parkingTime = parkingTime;
	}
	
	public Integer getLeaveTime() {
		return leaveTime;
	}
	public void setLeaveTime(Integer leaveTime) {
		this.leaveTime = leaveTime;
	}
	@JsonIgnore
	public void copy(ResUserOrder ruo) {  
		this.setOrderNo(ruo.getOrderNo());
		this.setPlateNumber(ruo.getPlateNo()); 
		this.setOrderTime(ruo.getCreateTime());
		this.setId(ruo.getId());
		this.setStartTime(ruo.getBeginTime()); 
		this.setPayTime(ruo.getUpdateTime());
		this.setEndTime(ruo.getEndTime());
		this.setStatus(ruo.getStatus().shortValue());
		this.setStallName(ruo.getStallName());
		this.setPrefectureName(ruo.getPreName());   
		this.setTotalAmount(ruo.getTotalAmount());
		this.setActualAmount(ruo.getActualAmount());
		this.setCouponAmount(ruo.getCouponAmount());
		this.setPayType(ruo.getPayType().shortValue());
		long day = 0;
		long hour = 0;
		long min = 0;
		long time = (this.getEndTime().getTime()-this.getStartTime().getTime())/(60*1000L);
		day = time / (24*60);
		hour =( time % (24*60) ) / 60;
		min = time % 60;
		StringBuffer parkingTime = new StringBuffer();
		if(day!=0) {
			parkingTime.append(day);
			parkingTime.append("天");
			parkingTime.append(hour);
			parkingTime.append("时"); 
		}else if(hour!=0) {
			parkingTime.append(hour);
			parkingTime.append("时");
		} 
		parkingTime.append(min);
		parkingTime.append("分");
		this.setParkingTime(parkingTime.toString()); 
	} 
}
