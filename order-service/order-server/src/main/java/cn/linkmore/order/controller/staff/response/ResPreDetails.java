package cn.linkmore.order.controller.staff.response;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车场详情")
public class ResPreDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("车位使用数")
	private int stallUseNumber;
	@ApiModelProperty("总车位数")
	private int totalStallNumber;
	@ApiModelProperty("固定车位使用数")
	private int entStallUseNumber;
	@ApiModelProperty("固定总车位数")
	private int entTotalStallNumber;
	@ApiModelProperty("临停(个人)车位使用数")
	private int appStallUseNumber;
	@ApiModelProperty("临停(个人)总车位数")
	private int appTotalStallNumber;
	
	@ApiModelProperty("固定自用")
	private int entHelpOneself;
	@ApiModelProperty("固定授权")
	private int entAuth;
	@ApiModelProperty("固定车位使用次数")
	private int entStallUseCount;

	@ApiModelProperty("临停订单收入")
	private double appOrderIncome;
	@ApiModelProperty("临停订单数量")
	private int appOrderCount;
	@ApiModelProperty("临停订单已完成")
	private int appOrderOver;
	@ApiModelProperty("临停订单进行中")
	private int appOrderUnfinished;
	@ApiModelProperty("车场层级数组")
	private List<String> floors;
	@ApiModelProperty("类型  0自营  2固定  4全部 5暂无数据(默认)")
	private short type = 0;
	@ApiModelProperty("车场id")
	private Long preId;
	@ApiModelProperty("车场名称")
	private String preName;
	
	public int getStallUseNumber() {
		return stallUseNumber;
	}
	public void setStallUseNumber(int stallUseNumber) {
		this.stallUseNumber = stallUseNumber;
	}
	public int getTotalStallNumber() {
		return totalStallNumber;
	}
	public void setTotalStallNumber(int totalStallNumber) {
		this.totalStallNumber = totalStallNumber;
	}
	public int getEntStallUseNumber() {
		return entStallUseNumber;
	}
	public void setEntStallUseNumber(int entStallUseNumber) {
		this.entStallUseNumber = entStallUseNumber;
	}
	public int getEntTotalStallNumber() {
		return entTotalStallNumber;
	}
	public void setEntTotalStallNumber(int entTotalStallNumber) {
		this.entTotalStallNumber = entTotalStallNumber;
	}
	public int getAppStallUseNumber() {
		return appStallUseNumber;
	}
	public void setAppStallUseNumber(int appStallUseNumber) {
		this.appStallUseNumber = appStallUseNumber;
	}
	public int getAppTotalStallNumber() {
		return appTotalStallNumber;
	}
	public void setAppTotalStallNumber(int appTotalStallNumber) {
		this.appTotalStallNumber = appTotalStallNumber;
	}
	public int getEntHelpOneself() {
		return entHelpOneself;
	}
	public void setEntHelpOneself(int entHelpOneself) {
		this.entHelpOneself = entHelpOneself;
	}
	public int getEntAuth() {
		return entAuth;
	}
	public void setEntAuth(int entAuth) {
		this.entAuth = entAuth;
	}
	public double getAppOrderIncome() {
		return appOrderIncome;
	}
	public void setAppOrderIncome(double appOrderIncome) {
		this.appOrderIncome = appOrderIncome;
	}
	public int getAppOrderCount() {
		return appOrderCount;
	}
	public void setAppOrderCount(int appOrderCount) {
		this.appOrderCount = appOrderCount;
	}
	public int getAppOrderOver() {
		return appOrderOver;
	}
	public void setAppOrderOver(int appOrderOver) {
		this.appOrderOver = appOrderOver;
	}
	public int getAppOrderUnfinished() {
		return appOrderUnfinished;
	}
	public void setAppOrderUnfinished(int appOrderUnfinished) {
		this.appOrderUnfinished = appOrderUnfinished;
	}
	public List<String> getFloors() {
		return floors;
	}
	public void setFloors(List<String> floors) {
		this.floors = floors;
	}
	public int getEntStallUseCount() {
		return entStallUseCount;
	}
	public void setEntStallUseCount(int entStallUseCount) {
		this.entStallUseCount = entStallUseCount;
	}
	public short getType() {
		return type;
	}
	public void setType(short type) {
		this.type = type;
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
}
