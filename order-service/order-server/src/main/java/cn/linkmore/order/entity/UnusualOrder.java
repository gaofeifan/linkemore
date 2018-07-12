package cn.linkmore.order.entity;

import java.util.Date;

public class UnusualOrder {
	
    private Long stallId;//车位id

    private String stallName;//车位名称

    private Long prefectureId;//车区id

    private Long orderId;//订单id

    private String orderNo;//订单编号

    private Date orderStartTime;//订单开始时间

    private Date orderEndTime;//订单结束时间

    private Short orderStatus;//订单状态

    private Short lockDownStatus;//降锁状态

    private Date lockDownTime;//降锁时间

    private Short category;//异常分类(0预约30分钟未降锁，1降锁超4个小时，2订单结束未释放车位，3关闭订单未释放车位，4挂起未释放车位)

    private Date orderOperateTime;//订单操作时间

    private String orderMobile;//订单联系人

    private Date createTime;//创建时间
    
    private Short statusHistory;//挂起和关闭状态

    public Short getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(Short statusHistory) {
		this.statusHistory = statusHistory;
	}

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
}