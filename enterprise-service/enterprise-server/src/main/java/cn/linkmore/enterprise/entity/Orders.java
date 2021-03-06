package cn.linkmore.enterprise.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.BeanUtils;
 

public class Orders {
	
	public static final int ORDERS_STATUS_UNPAY = 1;
	public static final int ORDERS_STATUS_PAIED = 2;
	public static final int ORDERS_STATUS_FINISH = 3;
	public static final int ORDERS_STATUS_CANCEL = 4;
	public static final int ORDERS_STATUS_REFOUND = 5;
	public static final int ORDERS_STATUS_PENDING = 6;
	public static final int ORDERS_STATUS_CLOSED = 7;
	
    private Long id;

    private Long userId;

    private Long preId;

    private Long stallId;

    private String orderNo;

    private Integer payType;

    private BigDecimal totalAmount;

    private BigDecimal actualAmount;

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

    private Short statusHistory;
    
    private Date statusTime;

    private byte[] ordersDetail;
    
    /**
     * 降锁状态
     */
    private Short lockDownStatus;
    
    /**
     * 降锁时间
     */
    private Date lockDownTime;

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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
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

    public Short getStatusHistory() {
        return statusHistory;
    }

    public void setStatusHistory(Short statusHistory) {
        this.statusHistory = statusHistory;
    }

    public byte[] getOrdersDetail() {
        return ordersDetail;
    }
    
    public void setOrdersDetail(byte[] ordersDetail) {
        this.ordersDetail = ordersDetail;
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

	public static Orders clone(Orders order,String code){
		if (order == null) {
			return null;
		}
		Orders bean = new Orders();
		BeanUtils.copyProperties(order, bean);
		bean.setDockId(code);
		return bean;
	}

	public Date getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}
	
}