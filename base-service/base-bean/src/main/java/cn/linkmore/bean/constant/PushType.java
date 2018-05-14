package cn.linkmore.bean.constant;
/**
 * 常量 - 推送类型
 * @author liwenlong
 * @version 2.0
 *
 */
public enum PushType {
	USER_APP_LOGOUT_NOTICE(0,"USER_APP_LOGOUT_NOTICE");
	public int type;
	public String id; 
	private PushType(int type, String id) {
		this.id = id;
		this.type = type;
	}
}
