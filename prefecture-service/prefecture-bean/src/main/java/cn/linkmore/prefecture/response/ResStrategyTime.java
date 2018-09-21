package cn.linkmore.prefecture.response;

import java.util.Date;
import java.util.List;

public class ResStrategyTime {
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
	
	/**
	 * 时间段list
	 */
    private List<ResStrategyTimeDetail> strategyTimeDetail;

    
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
	public List<ResStrategyTimeDetail> getStrategyTimeDetail() {
		return strategyTimeDetail;
	}
	public void setStrategyTimeDetail(List<ResStrategyTimeDetail> strategyTimeDetail) {
		this.strategyTimeDetail = strategyTimeDetail;
	}
	
}
