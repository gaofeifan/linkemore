package cn.linkmore.account.request;

import java.util.Date;
/**
 * 用户数据录入--请求beam
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
public class ReqCustomerInfoExport {

	/**
	 * 地推人员
	 */
	private String operator;
	
	/**
	 * 开始时间
	 */
	
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;

	public ReqCustomerInfoExport() {
		super();
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
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
