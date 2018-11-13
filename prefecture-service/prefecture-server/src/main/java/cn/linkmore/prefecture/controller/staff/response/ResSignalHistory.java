package cn.linkmore.prefecture.controller.staff.response;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class ResSignalHistory {

	@ApiModelProperty(value="")
	private List<ResSignalHistoryList> list;
	
	@ApiModelProperty(value="纵坐标数据  对应不同网关在对应时间点上的信号强度，参见下表 说明")
	private List<Object> xalas;


	public List<ResSignalHistoryList> getList() {
		return list;
	}

	public void setList(List<ResSignalHistoryList> list) {
		this.list = list;
	}

	public List<Object> getXalas() {
		return xalas;
	}

	public void setXalas(List<Object> list) {
		this.xalas = list;
	}
}
