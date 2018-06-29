package cn.linkmore.report.request;

public class ReqReportMonth {
	private Long cityId;//城市id
	private String preIds;//车区id
	private String startTime;//开始时间
	private String endTime;//结束时间
	private String statuIds;//订单状态
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
	public String getStatuIds() {
		return statuIds;
	}
	public void setStatuIds(String statuIds) {
		this.statuIds = statuIds;
	}
}
