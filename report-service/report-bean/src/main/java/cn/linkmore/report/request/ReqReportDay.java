package cn.linkmore.report.request;

import java.util.List;

public class ReqReportDay {
	private Long cityId;
	private List<Long> preIds;
	private String startTime;
	private String endTime;
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public List<Long> getPreIds() {
		return preIds;
	}
	public void setPreIds(List<Long> preIds) {
		this.preIds = preIds;
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
}
