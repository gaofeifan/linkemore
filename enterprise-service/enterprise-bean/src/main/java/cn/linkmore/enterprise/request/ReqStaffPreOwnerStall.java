package cn.linkmore.enterprise.request;

import java.util.Date;
import java.util.List;

public class ReqStaffPreOwnerStall {

	private List<Long> preIds;
	
	private Date startTime;
	
	private Date endTime;
	
	private short type = 0;

	public ReqStaffPreOwnerStall(List<Long> preIds, Date startTime, Date endTime) {
		super();
		this.preIds = preIds;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public ReqStaffPreOwnerStall(List<Long> preIds, Date startTime, Date endTime, short type) {
		this.preIds = preIds;
		this.startTime = startTime;
		this.endTime = endTime;
		this.type = type;
	}



	public ReqStaffPreOwnerStall() {}

	
	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public List<Long> getPreIds() {
		return preIds;
	}

	public void setPreIds(List<Long> preIds) {
		this.preIds = preIds;
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
	
	
}
