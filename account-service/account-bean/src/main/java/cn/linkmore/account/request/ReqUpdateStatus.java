package cn.linkmore.account.request;

import java.util.List;

/**
 * 修改状态
 * @author   GFF
 * @Date     2018年5月31日
 * @Version  v2.0
 */
public class ReqUpdateStatus {

	private List<Long> ids;
	
	private int status;

	
	
	public ReqUpdateStatus() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReqUpdateStatus(List<Long> ids, int status) {
		super();
		this.ids = ids;
		this.status = status;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
