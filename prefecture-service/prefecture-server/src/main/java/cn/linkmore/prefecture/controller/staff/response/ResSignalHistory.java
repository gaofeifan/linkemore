package cn.linkmore.prefecture.controller.staff.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ResSignalHistory {

	@ApiModelProperty(value="对应网关名称，一般指网关序列号后四位")
	private String name;
	
	@ApiModelProperty(value=" 信号强度数组 对应时间点上的信号强度，如果在时间点上未上报信 号，则为null")
	private List<Object> values;
	
	@ApiModelProperty(value="纵坐标数据  对应不同网关在对应时间点上的信号强度，参见下表 说明")
	private List<Object> xalas;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> objs) {
		this.values = objs;
	}

	public List<Object> getXalas() {
		return xalas;
	}

	public void setXalas(List<Object> list) {
		this.xalas = list;
	}
}
