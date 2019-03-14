package cn.linkmore.account.controller.open.response;

public class ResOpenAuth {

	private String auth_token;

	

	/**
	 * @return the auth_token
	 */
	public String getAuth_token() {
		return auth_token;
	}



	/**
	 * @param auth_token the auth_token to set
	 */
	public void setAuth_token(String auth_token) {
		this.auth_token = auth_token;
	}



	public  ResOpenAuth(String auth_token) {
		this.auth_token = auth_token;
	}

}
