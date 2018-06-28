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
	private double cashDealRate;//现金占交易额比例
	private double dealCostRate;//交易额占成本比例
	private double cashCostRate;//现金占成本比例cash_cost_rate
	private int fee;//费用= 成本-现金收入
	private int cost;//车区每天成本
	
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
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
	public double getCashDealRate() {
		return cashDealRate;
	}
	public void setCashDealRate(double cashDealRate) {
		this.cashDealRate = cashDealRate;
	}
	public double getDealCostRate() {
		return dealCostRate;
	}
	public void setDealCostRate(double dealCostRate) {
		this.dealCostRate = dealCostRate;
	}
	public double getCashCostRate() {
		return cashCostRate;
	}
	public void setCashCostRate(double cashCostRate) {
		this.cashCostRate = cashCostRate;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
}
