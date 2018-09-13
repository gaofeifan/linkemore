package cn.linkmore.prefecture.controller.staff.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车区列表")
public class ResStaffPreList {

	@ApiModelProperty(value="车区名称")
	private String preName;
	
	@ApiModelProperty(value="车区Id")
	private Long preId;
	
	@ApiModelProperty(value="异常订单数量")
	private int unusualOrder;
	
	@ApiModelProperty(value = "车位总数")
	private int preTypeStalls;
	
	@ApiModelProperty(value = "车位使用总数")
	private int preUseTypeStalls;

	public String getPreName() {
		return preName;
	}

	public void setPreName(String preName) {
		this.preName = preName;
	}

	public Long getPreId() {
		return preId;
	}

	public void setPreId(Long preId) {
		this.preId = preId;
	}

	public int getUnusualOrder() {
		return unusualOrder;
	}

	public void setUnusualOrder(int unusualOrder) {
		this.unusualOrder = unusualOrder;
	}

	public int getPreTypeStalls() {
		return preTypeStalls;
	}

	public void setPreTypeStalls(int preTypeStalls) {
		this.preTypeStalls = preTypeStalls;
	}

	public int getPreUseTypeStalls() {
		return preUseTypeStalls;
	}

	public void setPreUseTypeStalls(int preUseTypeStalls) {
		this.preUseTypeStalls = preUseTypeStalls;
	}
	
}
