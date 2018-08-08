package cn.linkmore.enterprise.controller.ent.response;

import io.swagger.annotations.ApiModelProperty;

public class ResEntTypeStalls {
	
	@ApiModelProperty(value = "类型")
	private Short type;
	
	@ApiModelProperty(value = "类型名称")
	private String typeName;
	
	@ApiModelProperty(value = "车位总数")
	private int preTypeStalls ;
	@ApiModelProperty(value = "车位使用总数")
	private int preUseTypeStalls ;
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
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
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
