package cn.linkmore.order.response;

import java.io.Serializable;
import java.util.Date;

public class ResRedisOrders implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3864536681218612967L;
	
	//1，未支付；2，已支付；3，已完成；4，已取消 5 退款6挂起订单7关闭订单
	public static final int ORDERS_STATUS_UNPAY = 1;
	public static final int ORDERS_STATUS_PAIED = 2;
	public static final int ORDERS_STATUS_FINISH = 3;
	public static final int ORDERS_STATUS_CANCEL = 4;
	public static final int ORDERS_STATUS_REFOUND = 5;
	public static final int ORDERS_STATUS_PENDING = 6;
	public static final int ORDERS_STATUS_CLOSED = 7;
	
	public static final int ORDERS_PAY_FREE = 1;
	public static final int ORDERS_PAY_COUPON = 2;
	public static final int ORDERS_PAY_ACCOUNT = 3;
	
	public static final Long CODE_NUM = 10000000l;
	
    private Long id;

    private Long userId;

    private Long preId;

    private Long stallId;

    private String orderNo;

    private Integer payType;

    private Double totalAmount;

    private Double actualAmount;

    private Integer status;

    private Date beginTime;

    private String username;

    private Date endTime;

    private String plateNo;

    private String stallImage;

    private String stallLocal;

    private String stallGuidance;

    private Date createTime;

    private Date updateTime;

    private String dockId;
    
    private ResRedisOrdersDetail ordersDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public Long getStallId() {
        return stallId;
    }

    public void setStallId(Long stallId) {
        this.stallId = stallId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo == null ? null : plateNo.trim();
    }

    public String getStallImage() {
        return stallImage;
    }

    public void setStallImage(String stallImage) {
        this.stallImage = stallImage == null ? null : stallImage.trim();
    }

    public String getStallLocal() {
        return stallLocal;
    }

    public void setStallLocal(String stallLocal) {
        this.stallLocal = stallLocal == null ? null : stallLocal.trim();
    }

    public String getStallGuidance() {
        return stallGuidance;
    }

    public void setStallGuidance(String stallGuidance) {
        this.stallGuidance = stallGuidance == null ? null : stallGuidance.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDockId() {
        return dockId;
    }

    public void setDockId(String dockId) {
        this.dockId = dockId == null ? null : dockId.trim();
    }

	public ResRedisOrdersDetail getOrdersDetail() {
		return ordersDetail;
	}

	public void setOrdersDetail(ResRedisOrdersDetail ordersDetail) {
		this.ordersDetail = ordersDetail;
	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", userId=" + userId + ", preId=" + preId + ", stallId=" + stallId + ", orderNo="
				+ orderNo + ", payType=" + payType + ", totalAmount=" + totalAmount + ", actualAmount=" + actualAmount
				+ ", status=" + status + ", beginTime=" + beginTime + ", username=" + username + ", endTime=" + endTime
				+ ", plateNo=" + plateNo + ", stallImage=" + stallImage + ", stallLocal=" + stallLocal
				+ ", stallGuidance=" + stallGuidance + ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", dockId=" + dockId + "]";
	}
    
    
}