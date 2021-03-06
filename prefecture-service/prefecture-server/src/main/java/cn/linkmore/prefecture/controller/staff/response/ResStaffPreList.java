package cn.linkmore.prefecture.controller.staff.response;

import java.util.HashMap;
import java.util.Map;

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
	
	@ApiModelProperty(value = "车区车位类型使用总数")
	private Map<String,ResEntTypeStalls> typeStalls = new HashMap<>();
	
	@ApiModelProperty(value = "车位空闲总数")
	private int preLeisureTypeStalls;

	@ApiModelProperty(value = "车位故障总数")
	private int preFaultTypeStalls;
	

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

	public int getPreLeisureTypeStalls() {
		return preLeisureTypeStalls;
	}

	public void setPreLeisureTypeStalls(int preLeisureTypeStalls) {
		this.preLeisureTypeStalls = preLeisureTypeStalls;
	}

	public int getPreFaultTypeStalls() {
		return preFaultTypeStalls;
	}

	public void setPreFaultTypeStalls(int preFaultTypeStalls) {
		this.preFaultTypeStalls = preFaultTypeStalls;
	}

	public Map<String, ResEntTypeStalls> getTypeStalls() {
		return typeStalls;
	}

	public void setTypeStalls(Map<String, ResEntTypeStalls> typeStalls) {
		this.typeStalls = typeStalls;
	}
}
