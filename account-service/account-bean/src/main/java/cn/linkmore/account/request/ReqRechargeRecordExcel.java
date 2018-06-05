package cn.linkmore.account.request;

import java.util.Date;

/**
 * 储值记录请求excel bean
 * @author   GFF
 * @Date     2018年5月31日
 * @Version  v2.0
 */
public class ReqRechargeRecordExcel {
	private Integer payType;
	private Integer payStatus;
	private Date startTime;
	private Date endTime;
	private String code;
	private String mobile;
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
