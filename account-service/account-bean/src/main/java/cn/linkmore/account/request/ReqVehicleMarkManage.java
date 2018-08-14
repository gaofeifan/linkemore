package cn.linkmore.account.request;

import java.util.Date;

public class ReqVehicleMarkManage {
	private Long id;

	private String vehUserId;

	private String vehMark;

	private Date createTime;

	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVehUserId() {
		return vehUserId;
	}

	public void setVehUserId(String vehUserId) {
		this.vehUserId = vehUserId == null ? null : vehUserId.trim();
	}

	public String getVehMark() {
		return vehMark;
	}

	public void setVehMark(String vehMark) {
		this.vehMark = vehMark == null ? null : vehMark.trim();
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
}
