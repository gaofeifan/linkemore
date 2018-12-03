package cn.linkmore.ops.ent.request;

import java.util.List;

public class ReqRentEnt {

	private Long id;

    private String companyName;

    private String startTime;

    private String endTime;

    private Long createEntId;
    
    private List<Long> stallIds;

    private String stallNames;
    
    private List<Long> preIds;

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
		this.companyName = companyName;
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

	public Long getCreateEntId() {
		return createEntId;
	}

	public void setCreateEntId(Long createEntId) {
		this.createEntId = createEntId;
	}

	public List<Long> getStallIds() {
		return stallIds;
	}

	public void setStallIds(List<Long> stallIds) {
		this.stallIds = stallIds;
	}

	public String getStallNames() {
		return stallNames;
	}

	public void setStallNames(String stallNames) {
		this.stallNames = stallNames;
	}

	public List<Long> getPreIds() {
		return preIds;
	}

	public void setPreIds(List<Long> preIds) {
		this.preIds = preIds;
	}
}
