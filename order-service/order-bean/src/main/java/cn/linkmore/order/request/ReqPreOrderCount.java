package cn.linkmore.order.request;

import java.util.List;

public class ReqPreOrderCount {

	private List<Long> preIds;
	
	private List<Long> stallIds;

	public List<Long> getPreIds() {
		return preIds;
	}

	public void setPreIds(List<Long> preIds) {
		this.preIds = preIds;
	}

	public List<Long> getStallIds() {
		return stallIds;
	}

	public void setStallIds(List<Long> stallIds) {
		this.stallIds = stallIds;
	}
	
}
