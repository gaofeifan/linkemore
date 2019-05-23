package cn.linkmore.prefecture.response;

import java.util.ArrayList;
import java.util.List;

public class ResStaffPres {

	private Long authId;
	
	private Long preId;
	
	private String preName;
	
	private ResStaffPresType preType = new ResStaffPresType();

	public Long getAuthId() {
		return authId;
	}

	public void setAuthId(Long authId) {
		this.authId = authId;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public ResStaffPresType getPreType() {
		return preType;
	}

	public void setPreType(ResStaffPresType preType) {
		this.preType = preType;
	}

}
