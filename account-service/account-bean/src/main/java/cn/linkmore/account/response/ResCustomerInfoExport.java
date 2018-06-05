package cn.linkmore.account.response;

import java.util.Date;
/**
 * 用户数据录入--响应bean
 * @author   GFF
 * @Date     2018年5月30日
 * @Version  v2.0
 */
public class ResCustomerInfoExport {


	private Long id;
	//地推人员
	private String operator;
	//地推人员名
	private String realname;
	//用户名
	private String username;
	private String sex;
	private String age;
	private int childNum;
	private String childAge;
	//车辆品牌型号
	private String brandModel;
	private String carColor;
	//汽车排量
	private String carDis;
	//停车原因
	private String stopCause;
	//创建时间
	private Date createTime;
	private String plateNo;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getChildAge() {
		return childAge;
	}

	public void setChildAge(String childAge) {
		this.childAge = childAge;
	}

	public String getBrandModel() {
		return brandModel;
	}

	public void setBrandModel(String brandModel) {
		this.brandModel = brandModel;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public String getCarDis() {
		return carDis;
	}

	public void setCarDis(String carDis) {
		this.carDis = carDis;
	}

	public String getStopCause() {
		return stopCause;
	}

	public void setStopCause(String stopCause) {
		this.stopCause = stopCause;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getChildNum() {
		return childNum;
	}

	public void setChildNum(int childNum) {
		this.childNum = childNum;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPlateNo() {
		return plateNo;
	}

	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
}
