package cn.linkmore.coupon.response;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ResDayTime{
	private String zdStartDate;
	private String zdEndDate;
	private String zdStartTime;
	private String zdEndTime;
	public String getZdStartDate() {
		return zdStartDate;
	}
	public void setZdStartDate(String zdStartDate) {
		this.zdStartDate = zdStartDate;
	}
	public String getZdEndDate() {
		return zdEndDate;
	}
	public void setZdEndDate(String zdEndDate) {
		this.zdEndDate = zdEndDate;
	}
	public String getZdStartTime() {
		return zdStartTime;
	}
	public void setZdStartTime(String zdStartTime) {
		this.zdStartTime = zdStartTime;
	}
	public String getZdEndTime() {
		return zdEndTime;
	}
	public void setZdEndTime(String zdEndTime) {
		this.zdEndTime = zdEndTime;
	} 
	public boolean check(){
		boolean flag = false; 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date today = new Date();
		String day = sdf.format(today); 
		sdf = new SimpleDateFormat("HH:mm:ss");
		String time = sdf.format(today);
		if(day.compareTo(this.getZdStartDate())>=0&&day.compareTo(this.getZdEndDate())<=0){
			if(time.compareTo(this.getZdStartTime())>=0&&time.compareTo(this.getZdEndTime())<=0){
				flag = true;
			}
		} 
		return flag;
	}
}
