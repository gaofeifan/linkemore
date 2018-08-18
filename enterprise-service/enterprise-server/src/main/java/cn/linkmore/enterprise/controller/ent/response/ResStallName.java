package cn.linkmore.enterprise.controller.ent.response;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车位名称")
public class ResStallName {

	@ApiModelProperty(value="车位名称 A01-A05")
	private String stallName;
	
	@ApiModelProperty(value="id")
	private String id;
	
	@ApiModelProperty(value="车位list")
	private List<ResStall> stalls = new ArrayList<>();

	public String getStallName() {
		return stallName;
	}

	public void setStallName(String stallName) {
		this.stallName = stallName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ResStall> getStalls() {
		return stalls;
	}

	public void setStalls(List<ResStall> stalls) {
		this.stalls = stalls;
	}
}
