package cn.linkmore.prefecture.controller.staff.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ResSignalHistoryList {

	@ApiModelProperty(value="对应网关名称，一般指网关序列号后四位")
	private String code;
	
	@ApiModelProperty(value="处理过的名称  格式为 网关一  网关二")
	private String name;
	
	@ApiModelProperty(value=" 信号强度数组 对应时间点上的信号强度，如果在时间点上未上报信 号，则为null")
	private List<Object> values;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Object> getValues() {
		return values;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}
	
	
}
