package cn.linkmore.report.request;

public class ReqReportDay {
	private Long cityId;
	private String preIds;
	private String startTime;
	private String endTime;
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	
	public String getPreIds() {
		return preIds;
	}
	public void setPreIds(String preIds) {
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
