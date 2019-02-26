package cn.linkmore.enterprise.request;

import java.util.Date;

public class ReqFixedRent {

	private Long fixedId;
	private Long entId;
	private String entName;
	private Long preId;
	private String preName;
	
	private String startTime;
	private String endTime;
	private String linkPhone;

	private String stallIds;
	private String stallNames;
	private String plateNos;
	
    private Long createUserId;
    private Date createTime;
    private Long updateUserId;
    private Date updateTime;
    
    private Short status;

	public Long getFixedId() {
		return fixedId;
	}

	public void setFixedId(Long fixedId) {
		this.fixedId = fixedId;
	}

	public Long getEntId() {
		return entId;
	}

	public void setEntId(Long entId) {
		this.entId = entId;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
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
		this.preName = preName;
	}



	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getStallIds() {
		return stallIds;
	}

	public void setStallIds(String stallIds) {
		this.stallIds = stallIds;
	}

	public String getStallNames() {
		return stallNames;
	}

	public void setStallNames(String stallNames) {
		this.stallNames = stallNames;
	}

	public String getPlateNos() {
		return plateNos;
	}

	public void setPlateNos(String plateNos) {
		this.plateNos = plateNos;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
    

}
