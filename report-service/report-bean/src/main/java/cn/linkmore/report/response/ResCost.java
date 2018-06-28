package cn.linkmore.report.response;
/**
 * 订单数量
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResCost {
	private Long cityId;//城市id
	private String cityName;//城市名称
	private Long preId;//车区id
	private String preName;//车区名称
	private int monthRent;//月租金
	private int stallTotal;//总车位数
	
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
	public int getMonthRent() {
		return monthRent;
	}
	public void setMonthRent(int monthRent) {
		this.monthRent = monthRent;
	}
	public int getStallTotal() {
		return stallTotal;
	}
	public void setStallTotal(int stallTotal) {
		this.stallTotal = stallTotal;
	}
}
