package cn.linkmore.prefecture.request;

import java.util.Date;

/**
 * 车位锁升降日志-Excel导出
 * @author jiaohanbin
 * @version 2.0
 */
public class ReqLockOperateLogExcel {
	
	private Integer source;
	private Integer operation;
	private Date startTime;
	private Date endTime;
	private Integer status;
	
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getOperation() {
		return operation;
	}
	public void setOperation(Integer operation) {
		this.operation = operation;
	}
	
}
