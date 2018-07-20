package cn.linkmore.enterprise.controller.ent.request;

import java.util.List;

public class ReqStaffAuthBind {

	private Long staffId;

	private List<Long> authIds;

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public List<Long> getAuthIds() {
		return authIds;
	}

	public void setAuthIds(List<Long> authIds) {
		this.authIds = authIds;
	}
}
