package cn.linkmore.report.response;
/**
 * 运营时长=日单量*均停时长
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResRunTime {
	private Long cityId;//城市id
	private String cityName;//车区名称
	private Long preId;//车区id
	private String preName;//车区名称
	private String day;//日期
	private int dayTotal;//新增订单数量
	private int totalTime;//订单总时长
	private int stallTotal;//车位总数
	private Double rdl;//日单量
	private Double jtsc;//均停时长
	private Double runtime;//运营时长
	
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
	public int getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(int totalTime) {
		this.totalTime = totalTime;
	}
	public int getStallTotal() {
		return stallTotal;
	}
	public void setStallTotal(int stallTotal) {
		this.stallTotal = stallTotal;
	}
	public Double getRdl() {
		return rdl;
	}
	public void setRdl(Double rdl) {
		this.rdl = rdl;
	}
	public Double getJtsc() {
		return jtsc;
	}
	public void setJtsc(Double jtsc) {
		this.jtsc = jtsc;
	}
	public Double getRuntime() {
		return runtime;
	}
	public void setRuntime(Double runtime) {
		this.runtime = runtime;
	}
}
