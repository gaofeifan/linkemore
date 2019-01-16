package cn.linkmore.account.response;

import cn.linkmore.annotation.GColumn;
import cn.linkmore.annotation.GTable;

@GTable(vlaue="v_vehicle_mark_manage")
public class ResVechicleMark {
	@GColumn
	private Long id;
	
	private Long userId;

	@GColumn
	private String vehMark;
	
	private Boolean rentPlateFlag;

	public Boolean getRentPlateFlag() {
		return rentPlateFlag;
	}

	public void setRentPlateFlag(Boolean rentPlateFlag) {
		this.rentPlateFlag = rentPlateFlag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

}

