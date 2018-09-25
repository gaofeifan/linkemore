package cn.linkmore.order.controller.staff.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("异常订单响应封装Bean")
public class UnusualOrderResponseBean {
	@ApiModelProperty(value = "车位ID")
	private Long stallId;

	@ApiModelProperty(value = "车位名称")
	private String stallName;

	@ApiModelProperty(value = "专区id")
	private Long prefectureId;

	@ApiModelProperty(value = "订单id")
	private Long orderId;

	@ApiModelProperty(value = "订单编号")
	private String orderNo;

	@ApiModelProperty(value = "订单开始时间")
	private Date orderStartTime;

	@ApiModelProperty(value = "订单结束时间")
	private Date orderEndTime;
	
	@ApiModelProperty(value = "类型[1,预约、2,空闲、3,异常 ]")
	private int type;

	@ApiModelProperty(value = "订单状态：1，未支付（预约中）；2，已支付；3，已完成；4，已取消 5 退款，6挂起订单，7关闭订单")
	private Short orderStatus;

	@ApiModelProperty(value = "降锁状态:null未操作，0失败，1成功")
	private Short lockDownStatus;

	@ApiModelProperty(value = "锁状态(1升起、2降下)")
	private int lockStatus;

	@ApiModelProperty(value = "降锁时间")
	private Date lockDownTime;

	@ApiModelProperty(value = "异常分类(0预约30分钟未降锁，1降锁超4个小时，2订单结束车位锁异常，3关闭订单未释放车位，4挂起未释放车位)")
	private Short category;

	@ApiModelProperty(value = "订单操作时间[挂起关闭]")
	private Date orderOperateTime;

	@ApiModelProperty(value = "订单用户电话")
	private String orderMobile;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "操作时间（具体的支付  关闭   完成  挂起时间）")
	private String operationDate;

	@ApiModelProperty(value = "操作类型   0已预约(待支付) 1已关闭 2已完成 3已挂起 4已降锁  ")
	private String operationType;

	public Long getStallId() {
		return stallId;
	}

	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName == null ? null : stallName.trim();
	}

	public Long getPrefectureId() {
		return prefectureId;
	}

	public void setPrefectureId(Long prefectureId) {
		this.prefectureId = prefectureId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public Date getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(Date orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public Date getOrderEndTime() {
		return orderEndTime;
	}

	public void setOrderEndTime(Date orderEndTime) {
		this.orderEndTime = orderEndTime;
	}

	public Short getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Short orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Short getLockDownStatus() {
		return lockDownStatus;
	}

	public void setLockDownStatus(Short lockDownStatus) {
		this.lockDownStatus = lockDownStatus;
	}

	@JsonFormat(timezone = "GMT+8", pattern = "MM/dd HH:mm:ss")
	public Date getLockDownTime() {
		return lockDownTime;
	}

	public void setLockDownTime(Date lockDownTime) {
		this.lockDownTime = lockDownTime;
	}

	public Short getCategory() {
		return category;
	}

	public void setCategory(Short category) {
		this.category = category;
	}

	public Date getOrderOperateTime() {
		return orderOperateTime;
	}

	public void setOrderOperateTime(Date orderOperateTime) {
		this.orderOperateTime = orderOperateTime;
	}

	public String getOrderMobile() {
		return orderMobile;
	}

	public void setOrderMobile(String orderMobile) {
		this.orderMobile = orderMobile == null ? null : orderMobile.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(int lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}