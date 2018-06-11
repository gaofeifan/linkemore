package cn.linkmore.account.request;

/**
 * 更新账户名称请求bean
 * @author   GFF
 * @Date     2018年6月11日
 * @Version  v2.0
 */
public class ReqUpdateAccount {

	private Long userId;
	
	private String accountName;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	
}
