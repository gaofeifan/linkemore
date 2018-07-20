package cn.linkmore.enterprise.controller.ent.request;

import java.util.List;

public class ReqStaffAuth {

	private List<Long> staffIds;

	private List<Long> authId;

	public List<Long> getStaffIds() {
		return staffIds;
	}

	public void setStaffIds(List<Long> staffIds) {
		this.staffIds = staffIds;
	}

	public List<Long> getAuthId() {
		return authId;
	}

	public void setAuthId(List<Long> authId) {
		this.authId = authId;
	}

	
}
