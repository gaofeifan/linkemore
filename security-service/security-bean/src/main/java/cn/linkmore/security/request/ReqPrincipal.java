package cn.linkmore.security.request;


import java.io.Serializable;

/**
 * Principal
 * <br/>Base Framework
 * @author liwenlong.net
 * @version 1.0
 */
public class ReqPrincipal implements Serializable {

	private static final long serialVersionUID = 5798882004228239559L;

	/** ID */
	private Long id;

	/** 账号 */
	private String account;


	public ReqPrincipal(Long id,String account){
		this.id = id;
		this.account = account;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}