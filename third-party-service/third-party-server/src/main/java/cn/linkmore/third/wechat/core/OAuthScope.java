package cn.linkmore.third.wechat.core;

/**
 * 消息类型
 * @author liwl
 * @version 1.0
 */

public enum OAuthScope {
	/**
	 * 用户openid
	 */
	Base("snsapi_base"),
	/**
	 * 用户信息
	 */
	Userinfo("userinfo");

	private String name;

	private OAuthScope(String name) {
	     this.name = name;
	}

	@Override
	public String toString(){
		return this.name;
	}

}
