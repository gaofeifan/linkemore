package cn.linkmore.enterprise.entity;

import java.util.Date;

public class RentEnt {
    private Long id;

    private String companyName;

    private Date startTime;

    private Date endTime;

    private Long createEntId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
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

    public Long getCreateEntId() {
        return createEntId;
    }

    public void setCreateEntId(Long createEntId) {
        this.createEntId = createEntId;
    }
}