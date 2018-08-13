package cn.linkmore.account.request;

import java.util.Date;

public class ReqCustomerInfoImport {
	private Long id;

	private Long adminId;

	private String sexName;

	private Long sex;

	private String ageName;

	private Long age;

	private String childAgeName;

	private String childAge;

	private String modelName;

	private Integer model;

	private String brandName;

	private Integer brandId;

	private String carColourName;

	private Long carColour;

	private String carDisName;

	private Long carDis;

	private String stopCauseName;

	private Long stopCause;

	private Date createTime;

	private Date updateTime;

	private Short childNum;

	private Short childExist;

	private String userPlateNum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getSexName() {
		return sexName;
	}

	public void setSexName(String sexName) {
		this.sexName = sexName == null ? null : sexName.trim();
	}

	public Long getSex() {
		return sex;
	}

	public void setSex(Long sex) {
		this.sex = sex;
	}

	public String getAgeName() {
		return ageName;
	}

	public void setAgeName(String ageName) {
		this.ageName = ageName == null ? null : ageName.trim();
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getChildAgeName() {
		return childAgeName;
	}

	public void setChildAgeName(String childAgeName) {
		this.childAgeName = childAgeName == null ? null : childAgeName.trim();
	}

	public String getChildAge() {
		return childAge;
	}

	public void setChildAge(String childAge) {
		this.childAge = childAge == null ? null : childAge.trim();
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName == null ? null : modelName.trim();
	}

	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName == null ? null : brandName.trim();
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getCarColourName() {
		return carColourName;
	}

	public void setCarColourName(String carColourName) {
		this.carColourName = carColourName == null ? null : carColourName.trim();
	}

	public Long getCarColour() {
		return carColour;
	}

	public void setCarColour(Long carColour) {
		this.carColour = carColour;
	}

	public String getCarDisName() {
		return carDisName;
	}

	public void setCarDisName(String carDisName) {
		this.carDisName = carDisName == null ? null : carDisName.trim();
	}

	public Long getCarDis() {
		return carDis;
	}

	public void setCarDis(Long carDis) {
		this.carDis = carDis;
	}

	public String getStopCauseName() {
		return stopCauseName;
	}

	public void setStopCauseName(String stopCauseName) {
		this.stopCauseName = stopCauseName == null ? null : stopCauseName.trim();
	}

	public Long getStopCause() {
		return stopCause;
	}

	public void setStopCause(Long stopCause) {
		this.stopCause = stopCause;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Short getChildNum() {
		return childNum;
	}

	public void setChildNum(Short childNum) {
		this.childNum = childNum;
	}

	public Short getChildExist() {
		return childExist;
	}

	public void setChildExist(Short childExist) {
		this.childExist = childExist;
	}

	public String getUserPlateNum() {
		return userPlateNum;
	}

	public void setUserPlateNum(String userPlateNum) {
		this.userPlateNum = userPlateNum == null ? null : userPlateNum.trim();
	}
}
