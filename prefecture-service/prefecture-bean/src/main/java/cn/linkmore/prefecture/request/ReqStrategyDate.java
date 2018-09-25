package cn.linkmore.prefecture.request;

import java.util.Date;
import java.util.List;


public class ReqStrategyDate {

    private Long id;

    private String name;

    private String detail;

    private Long createUserId;

    private String createUserName;

    private Date createTime;

    private Long updateUserId;

    private String updateUserName;

    private Date updateTime;

    private String startDate;
    
    private String stopDate;

    private Byte datetype;

    private Byte status;

    private List<ReqStrategyDateDetail> strategyDateDetail;

    private String dateGroup;
    
    public String getDateGroup() {
		return dateGroup;
	}

	public void setDateGroup(String dateGroup) {
		this.dateGroup = dateGroup;
	}

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
        this.name = name == null ? null : name.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
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
        this.createUserName = createUserName == null ? null : createUserName.trim();
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
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }



	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStopDate() {
		return stopDate;
	}

	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}

	public Byte getDatetype() {
        return datetype;
    }

    public void setDatetype(Byte datetype) {
        this.datetype = datetype;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

	public List<ReqStrategyDateDetail> getStrategyDateDetail() {
		return strategyDateDetail;
	}

	public void setStrategyDateDetail(List<ReqStrategyDateDetail> strategyDateDetail) {
		this.strategyDateDetail = strategyDateDetail;
	}






    
}