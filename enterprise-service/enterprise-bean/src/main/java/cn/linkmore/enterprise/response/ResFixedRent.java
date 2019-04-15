package cn.linkmore.enterprise.response;

import java.util.Date;
import java.util.List;

public class ResFixedRent {
	private Long fixedId;
	private Date startTime;
    private Date endTime;
    private String linkPhone;
    
    private List<String> plateNo;
    private List<String> userName;
    private List<ResStall> stall;
    
    private String createUserName;
    private Date createTime;
    private String updateUserName;
    private Date updateTime;
    
    
	public Long getFixedId() {
		return fixedId;
	}
	public void setFixedId(Long fixedId) {
		this.fixedId = fixedId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public List<String> getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(List<String> plateNo) {
		this.plateNo = plateNo;
	}
	public List<String> getUserName() {
		return userName;
	}
	public void setUserName(List<String> userName) {
		this.userName = userName;
	}
	public List<ResStall> getStall() {
		return stall;
	}
	public void setStall(List<ResStall> stall) {
		this.stall = stall;
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
    
    
}

