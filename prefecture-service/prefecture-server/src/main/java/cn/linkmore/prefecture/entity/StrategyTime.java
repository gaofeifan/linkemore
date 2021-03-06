package cn.linkmore.prefecture.entity;

import java.util.Date;

public class StrategyTime {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 策略名
	 */
	private String name;
	/**
	 * 简介
	 */
	private String detail;
	
	/**
	 * 是否为共用  =1 是, !=1 否
	 */
	private Byte isPublic;
	 
	/**
	 * 新建用户id
	 */
	private Long createUserId;
	/**
	 * 新建用户名
	 */
	private String createUserName;
	/**
	 * 新建时间
	 */
	private Date createTime;
	/**
	 * 修改用户id
	 */
	private Long updateUserId;
	/**
	 * 修改用户名
	 */
	private String updateUserName;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 状态 0默认１关闭 ２开启 ３过期
	 */
	private Integer status;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Byte getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Byte isPublic) {
		this.isPublic = isPublic;
	}
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}



}
