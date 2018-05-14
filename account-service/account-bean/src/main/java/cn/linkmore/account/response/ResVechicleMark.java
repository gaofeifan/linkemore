package cn.linkmore.account.response;

import cn.linkmore.annotation.GColumn;
import cn.linkmore.annotation.GTable;

@GTable(vlaue="v_vehicle_mark_manage")
public class ResVechicleMark {
	@GColumn
	private Long id;

	@GColumn
	private String vehMark;

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

}

