package cn.linkmore.report.response;
/**
 * 收入信息
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResIncome {
	private Long cityId;//城市id
	private String cityName;//城市名称
	private Long preId;//车区id
	private String preName;//车区名称
	private String day;//日期
	private double totalAmount;//交易额
	private double actualAmount;//现金收入
	private double rate;//现金占交易额比例
	
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
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
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
}
