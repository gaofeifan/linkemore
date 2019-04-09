package cn.linkmore.enterprise.controller.app.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("长租车位授权")
public class ResAuthRentStall {

	@ApiModelProperty(value = "是否有未完成操作")
	private Boolean isHave = false;

	@ApiModelProperty(value = "可用车位数量")
	private int num;

	@ApiModelProperty(value = "用户车位数据集")
	private List<ResRentUser> rentUsers;

	public Boolean getIsHave() {
		return isHave;
	}

	public void setIsHave(Boolean isHave) {
		this.isHave = isHave;
	}

	public int getNum() {
		if (rentUsers != null && rentUsers.size() != 0) {
			int n = 0;
			for (ResRentUser resRentUser : rentUsers) {
				for (ResRentUserStall stall : resRentUser.getRentUserStalls()) {
					n++;
				}
			}
			return num = n;
		}
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<ResRentUser> getRentUsers() {
		return rentUsers;
	} 

	public void setRentUsers(List<ResRentUser> rentUsers) {
		this.rentUsers = rentUsers;
	}

}
