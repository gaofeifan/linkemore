package cn.linkmore.order.controller.staff.response;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车场列表")
public class ResPreList {

	@ApiModelProperty("车区id")
	private Long preId;
	@ApiModelProperty("车区名称")
	private String preName;
	@ApiModelProperty("车区类型")
	private ResPreListType listType = new ResPreListType();
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
	public ResPreListType getListType() {
		return listType;
	}
	public void setListType(ResPreListType listType) {
		this.listType = listType;
	}
	
	
}
