package cn.linkmore.enterprise.request;

import java.util.List;

public class ReqOperateBind {

	private Long id;

	private List<ReqStallList> stallList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ReqStallList> getStallList() {
		return stallList;
	}

	public void setStallList(List<ReqStallList> stallList) {
		this.stallList = stallList;
	}
}
