package cn.linkmore.account.request;

public class ReqVehicleMark {
	
	private String vehMark;

	private Long userId;
	//更换车牌号
	private String newpl;
	
	public String getVehMark() {
		return vehMark;
	}

	public void setVehMark(String vehMark) {
		this.vehMark = vehMark;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNewpl() {
		return newpl;
	}

	public void setNewpl(String newpl) {
		this.newpl = newpl;
	}
	
}

