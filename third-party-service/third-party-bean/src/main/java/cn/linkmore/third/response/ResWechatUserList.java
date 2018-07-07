package cn.linkmore.third.response;

import java.util.List;

public class ResWechatUserList {

	private Integer total;
	
	private Integer count;
	
	private List<String> openIds;
	
	private String nextOpenid;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<String> getOpenIds() {
		return openIds;
	}

	public void setOpenIds(List<String> openIds) {
		this.openIds = openIds;
	}

	public String getNextOpenid() {
		return nextOpenid;
	}

	public void setNextOpenid(String nextOpenid) {
		this.nextOpenid = nextOpenid;
	}

	@Override
	public String toString() {
		return "ResWechatUserList [total=" + total + ", count=" + count + ", openIds=" + openIds + ", nextOpenid="
				+ nextOpenid + "]";
	}
	
	
}
