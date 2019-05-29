package cn.linkmore.ops.admin.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车区列表满足条件时展示详情")
public class ResPreListAndDetails {
	@ApiModelProperty("车场列表")
	private List<ResPreList> preLists;
	@ApiModelProperty("是否查询出该车区的详情数据  默认为0 否 1 是  ")
	private short type = 0;
	@ApiModelProperty("当type字段为1时存在详情数据")
	private ResPreDetails details;
	public List<ResPreList> getPreLists() {
		return preLists;
	}
	public void setPreLists(List<ResPreList> preLists) {
		this.preLists = preLists;
	}
	public short getType() {
		return type;
	}
	public void setType(short type) {
		this.type = type;
	}
	public ResPreDetails getDetails() {
		return details;
	}
	public void setDetails(ResPreDetails details) {
		this.details = details;
	}
	
}
