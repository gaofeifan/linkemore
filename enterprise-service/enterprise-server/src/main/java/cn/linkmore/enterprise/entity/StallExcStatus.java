package cn.linkmore.enterprise.entity;

import java.util.Date;

/**
 * 车位异常状态
 * @author   GFF
 * @Date     2018年8月6日
 * @Version  v2.0
 */
public class StallExcStatus {
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
	private Short excStatus;
	
	/**
	 *  创建时间
	 */ 
	private Date createTime;

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

	public Short getExcStatus() {
		return excStatus;
	}

	public void setExcStatus(Short excStatus) {
		this.excStatus = excStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
