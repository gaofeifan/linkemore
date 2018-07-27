package cn.linkmore.ops.ent.request;

public class ReqBindStaffAuth {

	private Long staffId;
	
	private String authIds;

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getAuthIds() {
		return authIds;
	}

	public void setAuthIds(String authIds) {
		this.authIds = authIds;
	}
	
}
