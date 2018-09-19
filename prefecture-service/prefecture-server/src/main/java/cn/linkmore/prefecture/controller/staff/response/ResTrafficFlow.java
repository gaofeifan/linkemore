package cn.linkmore.prefecture.controller.staff.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel("近7-15-30日车流量")
public class ResTrafficFlow {

	@ApiModelProperty(value="总数量")
	private Integer totalNumber;
	
	@ApiModelProperty(value="车流量列表")
	private List<ResTrafficFlowList> tfs;

	public List<ResTrafficFlowList> getTfs() {
		return tfs;
	}

	public void setTfs(List<ResTrafficFlowList> tfs) {
		this.tfs = tfs;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}
	
	
}
