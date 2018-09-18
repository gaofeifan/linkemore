package cn.linkmore.account.controller.staff.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户信息录入数据")
public class ResCustomerInfo {
	private static final long serialVersionUID = -8674217114251385725L;
	@ApiModelProperty(value = "客户性别")
	private List<ResDict> sex;
	@ApiModelProperty(value = "客户年龄")
	private List<ResDict> age;
	@ApiModelProperty(value = "小孩年龄")
	private List<ResDict> childAge;
	@ApiModelProperty(value = "汽车色")
	private List<ResDict> color;
	@ApiModelProperty(value = "汽车排量")
	private List<ResDict> displacement;
	@ApiModelProperty(value = "停车原因")
	private List<ResDict> cause;
	@ApiModelProperty(value = "车辆信息")
	private String brandName;
	@ApiModelProperty(value = "用户信息：未录入 true，已录入为false")
	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public List<ResDict> getSex() {
		return sex;
	}

	public void setSex(List<ResDict> sex) {
		this.sex = sex;
	}

	public List<ResDict> getAge() {
		return age;
	}

	public void setAge(List<ResDict> age) {
		this.age = age;
	}

	public List<ResDict> getChildAge() {
		return childAge;
	}

	public void setChildAge(List<ResDict> childAge) {
		this.childAge = childAge;
	}

	public List<ResDict> getColor() {
		return color;
	}

	public void setColor(List<ResDict> color) {
		this.color = color;
	}

	public List<ResDict> getDisplacement() {
		return displacement;
	}

	public void setDisplacement(List<ResDict> displacement) {
		this.displacement = displacement;
	}

	public List<ResDict> getCause() {
		return cause;
	}

	public void setCause(List<ResDict> cause) {
		this.cause = cause;
	}
}
