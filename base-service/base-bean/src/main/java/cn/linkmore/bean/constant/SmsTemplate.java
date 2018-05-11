package cn.linkmore.bean.constant;

/**
 * Enum - 短信模板
 * @author liwenlong
 * @version 2.0
 *
 */
public enum SmsTemplate {
	USER_APP_LOGIN_CODE(1,"user_app_login_code"),
	SHARE_COUPON_NOTICE(2,"share_coupon_notice"),
	ORDER_SUSPEND_NOTICE(3,"order_suspend_notice"),
	ORDER_SEARCH_NOTICE_(4,"order_search_notice");   
	public int type;
	public String id;
	 
	private SmsTemplate(int type, String id) {
		this.id = id;
		this.type = type;
	} 
}
