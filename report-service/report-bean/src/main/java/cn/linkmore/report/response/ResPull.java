package cn.linkmore.report.response;
/**
 * 现场拉新
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResPull {
	private Long cityId;//城市id
	private String cityName;//车区名称
	private Long preId;//车区id
	private String preName;//车区名称
	private String day;//日期
	private int dayTotal;//新增拉新数量
	
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
	public int getDayTotal() {
		return dayTotal;
	}
	public void setDayTotal(int dayTotal) {
		this.dayTotal = dayTotal;
	}
	
}
