package cn.linkmore.prefecture.response;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class ResStrategyDate {

    private Long id;

    private String name;

    private String detail;

    private Long createUserId;

    private String createUserName;

    private Date createTime;

    private Long updateUserId;

    private String updateUserName;

    private Date updateTime;

    private Date startDate;

    private Date stopDate;

    private Byte datetype;

    private Byte status;

    private List<ResStrategyDateDetail> strategyDateDetail;
    
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }



    public Date getStopDate() {
		return stopDate;
	}

	public void setStopDate(Date stopDate) {
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

	public List<ResStrategyDateDetail> getStrategyDateDetail() {
		return strategyDateDetail;
	}

	public void setStrategyDateDetail(List<ResStrategyDateDetail> strategyDateDetail) {
		this.strategyDateDetail = strategyDateDetail;
	}


}