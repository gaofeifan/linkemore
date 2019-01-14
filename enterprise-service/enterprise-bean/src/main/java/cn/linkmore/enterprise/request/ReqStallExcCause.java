package cn.linkmore.enterprise.request;

import java.util.Date;


public class ReqStallExcCause {

	private Long id;
	
	private Long stallId;
	
	private Long excStatus;
	
	private Short status;
	
	private Date createTime;
	
	private String 	excRemak;

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

	public Long getExcStatus() {
		return excStatus;
	}

	public void setExcStatus(Long excStatus) {
		this.excStatus = excStatus;
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

	public String getExcRemak() {
		return excRemak;
	}

	public void setExcRemak(String excRemak) {
		this.excRemak = excRemak;
	}
	
	
	
}
