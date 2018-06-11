package cn.linkmore.account.request;

/**
 * 消息请求bean
 * @author   GFF
 * @Date     2018年6月9日
 * @Version  v2.0
 */
public class ReqNotice {

	private Long nid;
	
	private Long userId;

	public Long getNid() {
		return nid;
	}

	public void setNid(Long nid) {
		this.nid = nid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
}
