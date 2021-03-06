package cn.linkmore.report.response;
/**
 * 拉新费用
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResPullCost {
	private Long cityId;//城市id
	private String cityName;//城市名称
	private Long preId;//车区id
	private String preName;//车区名称
	private String day;//日期
	private double fee;//费用
	private int dayTotal;//当天拉新用户数量
	private double pullCost;//拉新成本
	private double actualAmount;//实际收入
	
	public double getPullCost() {
		return pullCost;
	}
	public double getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
	}
	public void setPullCost(double pullCost) {
		this.pullCost = pullCost;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
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
	public double getFee() {
		return fee;
	}
	public void setFee(double fee) {
		this.fee = fee;
	}
	public int getDayTotal() {
		return dayTotal;
	}
	public void setDayTotal(int dayTotal) {
		this.dayTotal = dayTotal;
	}
}
