package cn.linkmore.prefecture.request;

import java.io.Serializable;

public class ReqStallOperateBean  implements Serializable{
	
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 1319498234254480661L;

	/**
	 * 车位id
	 */
	private Long stallId;
	
	/**
	 * 分类
	 */
	private Short type;
	
	/**
	 * 原因ID
	 */
	private Long remarkId;
	
	/**
	 * 原因文字
	 */
	private String remark;
	public Long getStallId() {
		return stallId;
	}
	public void setStallId(Long stallId) {
		this.stallId = stallId;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public Long getRemarkId() {
		return remarkId;
	}
	public void setRemarkId(Long remarkId) {
		this.remarkId = remarkId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
