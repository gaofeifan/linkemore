package cn.linkmore.report.response;
/**
 * 用户数量
 * @author jiaohanbin
 * @version 2.0
 *
 */
public class ResUserNum {
	private String day;//日期
	private Integer sumTotal;//累计总数
	private Integer dayTotal;//新增用户数
	private Integer stallTotal;//车位总数
	private Double average;//单车位日均
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public Integer getSumTotal() {
		return sumTotal;
	}
	public void setSumTotal(Integer sumTotal) {
		this.sumTotal = sumTotal;
	}
	public Integer getDayTotal() {
		return dayTotal;
	}
	public void setDayTotal(Integer dayTotal) {
		this.dayTotal = dayTotal;
	}
	public Integer getStallTotal() {
		return stallTotal;
	}
	public void setStallTotal(Integer stallTotal) {
		this.stallTotal = stallTotal;
	}
	public Double getAverage() {
		return average;
	}
	public void setAverage(Double average) {
		this.average = average;
	}
}
