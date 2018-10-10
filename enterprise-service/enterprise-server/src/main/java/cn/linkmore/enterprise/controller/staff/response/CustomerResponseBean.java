package cn.linkmore.enterprise.controller.staff.response;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 客户信息录入数据源集合
 * 
 * @author liwl
 * @version 1.0
 *
 */
@ApiModel("用户信息录入数据")
public class CustomerResponseBean implements Serializable {
	/**
	 * serial id
	 */
	private static final long serialVersionUID = -8674217114251385725L;
	@ApiModelProperty(value = "客户性别")
	private List<DictBean> sex;
	@ApiModelProperty(value = "客户年龄")
	private List<DictBean> age;
	@ApiModelProperty(value = "小孩年龄")
	private List<DictBean> childAge;
	@ApiModelProperty(value = "汽车色")
	private List<DictBean> color;
	@ApiModelProperty(value = "汽车排量")
	private List<DictBean> displacement;
	@ApiModelProperty(value = "停车原因")
	private List<DictBean> cause;
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

	public List<DictBean> getSex() {
		return sex;
	}

	public void setSex(List<DictBean> sex) {
		this.sex = sex;
	}

	public List<DictBean> getAge() {
		return age;
	}

	public void setAge(List<DictBean> age) {
		this.age = age;
	}

	public List<DictBean> getChildAge() {
		return childAge;
	}

	public void setChildAge(List<DictBean> childAge) {
		this.childAge = childAge;
	}

	public List<DictBean> getColor() {
		return color;
	}

	public void setColor(List<DictBean> color) {
		this.color = color;
	}

	public List<DictBean> getDisplacement() {
		return displacement;
	}

	public void setDisplacement(List<DictBean> displacement) {
		this.displacement = displacement;
	}

	public List<DictBean> getCause() {
		return cause;
	}

	public void setCause(List<DictBean> cause) {
		this.cause = cause;
	}

}
