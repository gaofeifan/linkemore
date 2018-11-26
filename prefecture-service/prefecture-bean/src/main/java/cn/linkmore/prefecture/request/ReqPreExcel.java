package cn.linkmore.prefecture.request;

import java.util.Date;
/**
 * 车区列表查询条件-Excel导出
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ReqPreExcel {

	// 专区名称
	private String name;
	// 签约时间
	private Date startTime;
	// 有效期
	private Date endTime;
	// 状态 0启用 1禁用
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
