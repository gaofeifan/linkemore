package cn.linkmore.enterprise.controller.ent.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车位锁异常")
public class ReqStallExcCause {

	@ApiModelProperty(value="车位锁")
	private String stallLock;
	
	/**
	 *  1001  上升遇阻
	 *  1002  车锁被撞
	 *  1003  车位检测
	 */ 
	@ApiModelProperty(value="上报数据源")
	private String reportSource;
	
	@ApiModelProperty(value="上报数据源")
	private String reportInfo;

	public String getStallLock() {
		return stallLock;
	}

	public void setStallLock(String stallLock) {
		this.stallLock = stallLock;
	}

	public String getReportSource() {
		return reportSource;
	}

	public void setReportSource(String reportSource) {
		this.reportSource = reportSource;
	}

	public String getReportInfo() {
		return reportInfo;
	}

	public void setReportInfo(String reportInfo) {
		this.reportInfo = reportInfo;
	}

	@Override
	public String toString() {
		return "ReqStallExcCause [stallLock=" + stallLock + ", reportSource=" + reportSource + ", reportInfo="
				+ reportInfo + "]";
	}
	
	
}
