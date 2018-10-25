package cn.linkmore.order.response;

import java.math.BigDecimal;
import java.util.Date;

public class ResOrderOps {
	private String prefectureName;
	
	private String stallName;

    private Long id;

    private Long userId;

    private Long preId;

    private Long stallId;

    private String orderNo;

    private Integer payType;

    private BigDecimal totalAmount;

    private BigDecimal actualAmount;

    private Integer status;
    
    private Date statusTime;

    private Date beginTime;

    private String username;

    private Date endTime;

    private String plateNo;

    private Date createTime;

    private Date updateTime;

    private String dockId;
    
    private Short statusHistory;//挂起和关闭状态
    
    private Long createUserId;
    
    private String createUserName;

    public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public Short getStatusHistory() {
		return statusHistory;
	}

	public void setStatusHistory(Short statusHistory) {
		this.statusHistory = statusHistory;
	}
    
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
		this.orderNo = orderNo;
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

	public Date getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(Date statusTime) {
		this.statusTime = statusTime;
	}
}
