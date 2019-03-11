package cn.linkmore.order.controller.app.response;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("账单信息")
public class ResPayCheckout {
	@ApiModelProperty(value = "订单ID")
	private Long orderId;
	@ApiModelProperty(value = "专区名称")
	private String prefectureName;
	@ApiModelProperty(value = "车位名称")
	private String stallName;
	@ApiModelProperty(value = "车牌号")
	private String plateNumber;
	@ApiModelProperty(value = "开始时间")
	private Date startTime;
	@ApiModelProperty(value = "结束时间")
	private Date endTime;
	@ApiModelProperty(value = "停车时长")
	private Integer parkingTime;
	@ApiModelProperty(value = "停车券数量")
	private Integer couponCount;
	@ApiModelProperty(value = "支付分类[0账户余额，1支付宝，2微信，3银联]")
	private Short payType;
	@ApiModelProperty(value = "订单总额")
	private BigDecimal totalAmount;
	@ApiModelProperty(value = "账户余额")
	private BigDecimal accountAmount;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getPrefectureName() {
		return prefectureName;
	}
	public void setPrefectureName(String prefectureName) {
		this.prefectureName = prefectureName;
	}
	public String getStallName() {
		return stallName;
	}
	public void setStallName(String stallName) {
		this.stallName = stallName;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
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
	public Integer getParkingTime() {
		return parkingTime;
	}
	public void setParkingTime(Integer parkingTime) {
		this.parkingTime = parkingTime;
	}
	public Integer getCouponCount() {
		return couponCount;
	}
	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}
	public Short getPayType() {
		return payType;
	}
	public void setPayType(Short payType) {
		this.payType = payType;
	}
	public BigDecimal getTotalAmount() {
		if(this.totalAmount==null) {
			this.totalAmount = new BigDecimal(0d);
		}
		return this.totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public BigDecimal getAccountAmount() {
		if(this.accountAmount==null) {
			this.accountAmount = new BigDecimal(0d);
		}
		return this.accountAmount.setScale(2,   BigDecimal.ROUND_HALF_UP);
	}
	public void setAccountAmount(BigDecimal accountAmount) {
		this.accountAmount = accountAmount;
	} 
}
