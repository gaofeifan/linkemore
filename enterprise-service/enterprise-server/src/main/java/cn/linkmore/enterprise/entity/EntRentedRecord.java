package cn.linkmore.enterprise.entity;

import java.util.Date;

public class EntRentedRecord {
    private Long id;

    private Long entId;

    private Long entPreId;

    private Long plateNo;

    private Date downTime;

    private Date leaveTime;

    private Long stallId;

    private String stallName;

    private Long preId;

    private String preName;
    
    private String entName;
    
    private Long status;
    
    private Long userId;

    
    
    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEntId() {
        return entId;
    }

    public void setEntId(Long entId) {
        this.entId = entId;
    }

    public Long getEntPreId() {
        return entPreId;
    }

    public void setEntPreId(Long entPreId) {
        this.entPreId = entPreId;
    }

    public Long getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(Long plateNo) {
        this.plateNo = plateNo;
    }

    public Date getDownTime() {
        return downTime;
    }

    public void setDownTime(Date downTime) {
        this.downTime = downTime;
    }

    public Date getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Date leaveTime) {
        this.leaveTime = leaveTime;
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

    public Long getPreId() {
        return preId;
    }

    public void setPreId(Long preId) {
        this.preId = preId;
    }

    public String getPreName() {
        return preName;
    }

    public void setPreName(String preName) {
        this.preName = preName == null ? null : preName.trim();
    }

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}
    
}