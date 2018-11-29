package cn.linkmore.enterprise.request;

import java.util.Date;
import java.util.List;

public class ReqRentEnt {
    private Long id;

    private String companyName;

    private Date startTime;

    private Date endTime;

    private Long createEntId;
    
    private List<ReqRentEntStall > stalls;

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

	public List<ReqRentEntStall> getStalls() {
		return stalls;
	}

	public void setStalls(List<ReqRentEntStall> stalls) {
		this.stalls = stalls;
	}
}