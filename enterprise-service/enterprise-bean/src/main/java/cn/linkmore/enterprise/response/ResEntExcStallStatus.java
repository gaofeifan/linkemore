package cn.linkmore.enterprise.response;

import java.util.Date;

public class ResEntExcStallStatus {

	/**
	 *  id
	 */ 
	private Long id;
	
	/**
	 *  车位id
	 */ 
	private Long stallId;
	
	/**
	 *  状态 0未解决  1 已解决
	 */ 
	private Short status;
	
	/**
	 *  异常状态
	 */ 
	private Long excStatus;
	
	/**
	 *  创建时间
	 */ 
	private Date createTime;
	
	/**
	 *  异常描述
	 */ 
	private String excRemark;

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

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getExcRemark() {
		return excRemark;
	}

	public void setExcRemark(String excRemark) {
		this.excRemark = excRemark;
	}

	public void setExcStatus(Long excStatus) {
		this.excStatus = excStatus;
	}

	public Long getExcStatus() {
		return excStatus;
	}

}
