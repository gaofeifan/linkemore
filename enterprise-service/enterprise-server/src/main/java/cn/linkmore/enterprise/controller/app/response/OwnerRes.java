package cn.linkmore.enterprise.controller.app.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车位结果集")
public class OwnerRes {
	
	@ApiModelProperty(value = "是否有未完成操作")
	private Boolean isHave;
	
	@ApiModelProperty(value = "可用车位数量")
	private int num;
	
	@ApiModelProperty(value = "车区及车位结果")
	private List<OwnerPre> res;

	public Boolean getIsHave() {
		return isHave;
	}

	public void setIsHave(Boolean isHave) {
		this.isHave = isHave;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public List<OwnerPre> getRes() {
		return res;
	}

	public void setRes(List<OwnerPre> res) {
		this.res = res;
	}
}
