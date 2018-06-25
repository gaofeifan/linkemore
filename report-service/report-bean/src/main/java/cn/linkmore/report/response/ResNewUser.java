package cn.linkmore.report.response;
/**
 * 新增用户
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResNewUser {
	private int hzPull;//杭州拉新
	private int bjPull;//北京拉新
	private int hzCooperation = 0;//杭州合作转化
	private int bjCooperation = 0;//北京合作转化
	private int hzUnderTran = 0;//杭州线下转化
	private int bjUnderTran = 0;//北京线下转化
	private int natureTran ;//自然转化
	
	private Long cityId;//城市id
	private String cityName;//城市名称
	private String day;//日期
	private int dayTotal;//新增数量
	
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
	public int getHzPull() {
		return hzPull;
	}
	public void setHzPull(int hzPull) {
		this.hzPull = hzPull;
	}
	public int getBjPull() {
		return bjPull;
	}
	public void setBjPull(int bjPull) {
		this.bjPull = bjPull;
	}
	public int getHzCooperation() {
		return hzCooperation;
	}
	public void setHzCooperation(int hzCooperation) {
		this.hzCooperation = hzCooperation;
	}
	public int getBjCooperation() {
		return bjCooperation;
	}
	public void setBjCooperation(int bjCooperation) {
		this.bjCooperation = bjCooperation;
	}
	public int getHzUnderTran() {
		return hzUnderTran;
	}
	public void setHzUnderTran(int hzUnderTran) {
		this.hzUnderTran = hzUnderTran;
	}
	public int getBjUnderTran() {
		return bjUnderTran;
	}
	public void setBjUnderTran(int bjUnderTran) {
		this.bjUnderTran = bjUnderTran;
	}
	public int getNatureTran() {
		return natureTran;
	}
	public void setNatureTran(int natureTran) {
		this.natureTran = natureTran;
	}
}
