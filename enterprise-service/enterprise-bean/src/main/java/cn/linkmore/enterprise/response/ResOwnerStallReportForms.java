package cn.linkmore.enterprise.response;

import java.util.Date;

public class ResOwnerStallReportForms {

	private int useStall;
	
	private String useStallContrast;
	
	private int useStallCount;
	
	private String useStallCountContrast;
	
	private int oneselfUseStallNumber;
	
	private String oneselfUseStallNumberContrast;
	
	private int authStallNumber;
	
	private String authStallNumberContrast;
	
	private Double useDuration;
	
	private String useDurationContrast;

	private Date startTime;
	
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getUseStall() {
		return useStall;
	}

	public Double getUseDuration() {
		return useDuration;
	}

	public void setUseDuration(Double useDuration) {
		this.useDuration = useDuration;
	}

	public String getUseDurationContrast() {
		return useDurationContrast;
	}

	public void setUseDurationContrast(String useDurationContrast) {
		this.useDurationContrast = useDurationContrast;
	}

	public void setUseStall(int useStall) {
		this.useStall = useStall;
	}

	public String getUseStallContrast() {
		return useStallContrast;
	}

	public void setUseStallContrast(String useStallContrast) {
		this.useStallContrast = useStallContrast;
	}

	public int getUseStallCount() {
		return useStallCount;
	}

	public void setUseStallCount(int useStallCount) {
		this.useStallCount = useStallCount;
	}

	public String getUseStallCountContrast() {
		return useStallCountContrast;
	}

	public void setUseStallCountContrast(String useStallCountContrast) {
		this.useStallCountContrast = useStallCountContrast;
	}

	public int getOneselfUseStallNumber() {
		return oneselfUseStallNumber;
	}

	public void setOneselfUseStallNumber(int oneselfUseStallNumber) {
		this.oneselfUseStallNumber = oneselfUseStallNumber;
	}

	public String getOneselfUseStallNumberContrast() {
		return oneselfUseStallNumberContrast;
	}

	public void setOneselfUseStallNumberContrast(String oneselfUseStallNumberContrast) {
		this.oneselfUseStallNumberContrast = oneselfUseStallNumberContrast;
	}

	public int getAuthStallNumber() {
		return authStallNumber;
	}

	public void setAuthStallNumber(int authStallNumber) {
		this.authStallNumber = authStallNumber;
	}

	public String getAuthStallNumberContrast() {
		return authStallNumberContrast;
	}

	public void setAuthStallNumberContrast(String authStallNumberContrast) {
		this.authStallNumberContrast = authStallNumberContrast;
	}
	
	
}
