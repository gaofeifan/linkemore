package cn.linkmore.coupon.response;

public class ResCondition{ 
	private Short preLimit = 0;
	private Short timeLimit = 0;
	private  String[] prefectures; 
	private ResWeekTime wtb;
	private ResDayTime dtb;
	public Short getPreLimit() {
		return preLimit;
	}
	public void setPreLimit(Short preLimit) {
		this.preLimit = preLimit;
	}
	public Short getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(Short timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String[] getPrefectures() {
		return prefectures;
	}
	public void setPrefectures(String[] prefectures) {
		this.prefectures = prefectures;
	}
	public ResWeekTime getWtb() {
		return wtb;
	}
	public void setWtb(ResWeekTime wtb) {
		this.wtb = wtb;
	}
	public ResDayTime getDtb() {
		return dtb;
	}
	public void setDtb(ResDayTime dtb) {
		this.dtb = dtb;
	} 
}
