package cn.linkmore.order.controller.staff.response;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车场报表")
public class ResPreReportForms {

	@ApiModelProperty("类型为日时 为昨日时间  类型为周或月时为周月的开始时间")
	private Date startTime;
	
	@ApiModelProperty("类型为日时 为空  类型为周或月时为周月的结束时间")
	private Date endTime;
	
	@ApiModelProperty("固定使用车位数")
	private int entUseStallCount;
	@ApiModelProperty("固定使用车位环比")
	private String entUseStallRelative;
	@ApiModelProperty("固定 使用车位次数")
	private int entUseStallNumber;
	@ApiModelProperty("固定使用车位次数环比")
	private String entUseStallNumberRelative;
	
	@ApiModelProperty("固定自用车位数")
	private int entOneselfStallNumber;
	@ApiModelProperty("固定自用使用车位数环比")
	private String entOneselfStallRelative;
	@ApiModelProperty("固定授权数")
	private int entAuthStallNumber;
	@ApiModelProperty("固定授权车位次数环比")
	private String entAuthStallRelative;
	
	
	@ApiModelProperty("临停订单数")
	private int tempOrderNumber;
	@ApiModelProperty("临停订单环比")
	private String tempOrderRelative;
	@ApiModelProperty("临停收入")
	private double tempIncome;
	@ApiModelProperty("临停收入环比")
	private String tempIncomeRelative;
	
	@ApiModelProperty("临停预约收入")
	private double tempAppointmentIncome;
	@ApiModelProperty("临停预约收入环比")
	private String tempAppointmentRelative;
	@ApiModelProperty("临停扫码收入")
	private double tempScanCodeIncome;
	@ApiModelProperty("临停扫码收入环比")
	private String tempScanCodeRelative;
	@ApiModelProperty("临停分享收入")
	private double tempShareIncome;
	@ApiModelProperty("临停分享收入环比")
	private String tempShareRelative;
	
	@ApiModelProperty("所有的车位使用时长 (类型为double保留两位小数 单位小时)")
	private Double allStallUseTime;
	@ApiModelProperty("所有的车位使用时长环比")
	private String allStallUseTimeRelative;
	@ApiModelProperty("固定车位使用时长  (类型为double保留两位小数 单位小时)")
	private Double entStallUseTime;
	@ApiModelProperty("固定车位使用时长环比")
	private String entStallUseTimeRelative;
	@ApiModelProperty("临停车位使用时长 (类型为double保留两位小数 单位小时)")
	private Double tempStallUseTime;
	@ApiModelProperty("临停车位使用时长环比")
	private String tempStallUseTimeRelative;
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getEntUseStallCount() {
		return entUseStallCount;
	}
	public void setEntUseStallCount(int entUseStallCount) {
		this.entUseStallCount = entUseStallCount;
	}
	public String getEntUseStallRelative() {
		return entUseStallRelative;
	}
	public void setEntUseStallRelative(String entUseStallRelative) {
		this.entUseStallRelative = entUseStallRelative;
	}
	public int getEntUseStallNumber() {
		return entUseStallNumber;
	}
	public void setEntUseStallNumber(int entUseStallNumber) {
		this.entUseStallNumber = entUseStallNumber;
	}
	public String getEntUseStallNumberRelative() {
		return entUseStallNumberRelative;
	}
	public void setEntUseStallNumberRelative(String entUseStallNumberRelative) {
		this.entUseStallNumberRelative = entUseStallNumberRelative;
	}
	public int getEntOneselfStallNumber() {
		return entOneselfStallNumber;
	}
	public void setEntOneselfStallNumber(int entOneselfStallNumber) {
		this.entOneselfStallNumber = entOneselfStallNumber;
	}
	public String getEntOneselfStallRelative() {
		return entOneselfStallRelative;
	}
	public void setEntOneselfStallRelative(String entOneselfStallRelative) {
		this.entOneselfStallRelative = entOneselfStallRelative;
	}
	public int getEntAuthStallNumber() {
		return entAuthStallNumber;
	}
	public void setEntAuthStallNumber(int entAuthStallNumber) {
		this.entAuthStallNumber = entAuthStallNumber;
	}
	public String getEntAuthStallRelative() {
		return entAuthStallRelative;
	}
	public void setEntAuthStallRelative(String entAuthStallRelative) {
		this.entAuthStallRelative = entAuthStallRelative;
	}
	public int getTempOrderNumber() {
		return tempOrderNumber;
	}
	public void setTempOrderNumber(int tempOrderNumber) {
		this.tempOrderNumber = tempOrderNumber;
	}
	public String getTempOrderRelative() {
		return tempOrderRelative;
	}
	public void setTempOrderRelative(String tempOrderRelative) {
		this.tempOrderRelative = tempOrderRelative;
	}
	public double getTempIncome() {
		return tempIncome;
	}
	public void setTempIncome(double tempIncome) {
		this.tempIncome = tempIncome;
	}
	public String getTempIncomeRelative() {
		return tempIncomeRelative;
	}
	public void setTempIncomeRelative(String tempIncomeRelative) {
		this.tempIncomeRelative = tempIncomeRelative;
	}
	public double getTempAppointmentIncome() {
		return tempAppointmentIncome;
	}
	public void setTempAppointmentIncome(double tempAppointmentIncome) {
		this.tempAppointmentIncome = tempAppointmentIncome;
	}
	public String getTempAppointmentRelative() {
		return tempAppointmentRelative;
	}
	public void setTempAppointmentRelative(String tempAppointmentRelative) {
		this.tempAppointmentRelative = tempAppointmentRelative;
	}
	public double getTempScanCodeIncome() {
		return tempScanCodeIncome;
	}
	public void setTempScanCodeIncome(double tempScanCodeIncome) {
		this.tempScanCodeIncome = tempScanCodeIncome;
	}
	public String getTempScanCodeRelative() {
		return tempScanCodeRelative;
	}
	public void setTempScanCodeRelative(String tempScanCodeRelative) {
		this.tempScanCodeRelative = tempScanCodeRelative;
	}
	public double getTempShareIncome() {
		return tempShareIncome;
	}
	public void setTempShareIncome(double tempShareIncome) {
		this.tempShareIncome = tempShareIncome;
	}
	public String getTempShareRelative() {
		return tempShareRelative;
	}
	public void setTempShareRelative(String tempShareRelative) {
		this.tempShareRelative = tempShareRelative;
	}

	public Double getAllStallUseTime() {
		return allStallUseTime;
	}
	public void setAllStallUseTime(Double allStallUseTime) {
		this.allStallUseTime = allStallUseTime;
	}
	public String getAllStallUseTimeRelative() {
		return allStallUseTimeRelative;
	}
	public void setAllStallUseTimeRelative(String allStallUseTimeRelative) {
		this.allStallUseTimeRelative = allStallUseTimeRelative;
	}
	public Double getEntStallUseTime() {
		return entStallUseTime;
	}
	public void setEntStallUseTime(Double entStallUseTime) {
		this.entStallUseTime = entStallUseTime;
	}
	public String getEntStallUseTimeRelative() {
		return entStallUseTimeRelative;
	}
	public void setEntStallUseTimeRelative(String entStallUseTimeRelative) {
		this.entStallUseTimeRelative = entStallUseTimeRelative;
	}
	public Double getTempStallUseTime() {
		return tempStallUseTime;
	}
	public void setTempStallUseTime(Double tempStallUseTime) {
		this.tempStallUseTime = tempStallUseTime;
	}
	public String getTempStallUseTimeRelative() {
		return tempStallUseTimeRelative;
	}
	public void setTempStallUseTimeRelative(String tempStallUseTimeRelative) {
		this.tempStallUseTimeRelative = tempStallUseTimeRelative;
	}
	
}
