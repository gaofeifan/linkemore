package cn.linkmore.enterprise.request;

import java.util.Date;
import java.util.List;

public class ReqPreDetails {

	private String floor;
	
	private List<Long> stallIds;
	
	private Long preId;

	private short type = 0;
	
	private Date startTime;
	
	private Date endTime;
	
	private Date contrastStartTime;
	
	private Date contrastEndTime;
	
	private int stallCount = 1;
	
	
	public int getStallCount() {
		return stallCount;
	}

	public void setStallCount(int stallCount) {
		if(stallCount != 0) {
			this.stallCount = stallCount;
		}
	}

	public ReqPreDetails() {
	}

	public ReqPreDetails(String floor, List<Long> stallIds, Long preId, short type) {
		super();
		this.floor = floor;
		this.stallIds = stallIds;
		this.preId = preId;
		this.type = type;
	}

	public ReqPreDetails(String floor, List<Long> stallIds, Long preId) {
		super();
		this.floor = floor;
		this.stallIds = stallIds;
		this.preId = preId;
	}


	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public List<Long> getStallIds() {
		return stallIds;
	}

	public void setStallIds(List<Long> stallIds) {
		this.stallIds = stallIds;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
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

	public Date getContrastStartTime() {
		return contrastStartTime;
	}

	public void setContrastStartTime(Date contrastStartTime) {
		this.contrastStartTime = contrastStartTime;
	}

	public Date getContrastEndTime() {
		return contrastEndTime;
	}

	public void setContrastEndTime(Date contrastEndTime) {
		this.contrastEndTime = contrastEndTime;
	}
	
	
}
