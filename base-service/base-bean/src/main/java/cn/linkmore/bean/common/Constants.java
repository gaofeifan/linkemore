package cn.linkmore.bean.common;

/**
 * Constant - 常量
 * 
 * @author liwenlong
 * @version 2.0
 *
 */
public class Constants {
	public final static String ACCESS_TOKEN_HEADER_NAME = "X-Access-Auth-Token";
	
	public enum TradeType{
		//-1消费[未付款],0消费[账户余额],1消费[订单充值],2账户充值[订单充值],3账户充值[用户充值]
		ORDER_NONE_PAY(-1),
		ORDER_ACCOUNT_PAY(0),
		ORDER_RECHARGE_PAY ( 1),
		ORDER_RECHARGE (2),
		USER_RECHARGE ( 3); 
		public int type;
		private TradeType(int type) {
			this.type = type;
		}
		
	}
	
	public enum UserStaffStatus{
		OFF(0),ON(1);
		public int status;
		private UserStaffStatus(int status){
			this.status = status;
		}
	}
	
	public enum TradePayType{
		ACCOUNT(0),
		ALIPAY( 1),
		WECHAT ( 2), 
		APPLE (3),
		WECHAT_MINI (4),
		UNION(5),
		HUAWEI(6),
		XIAOMI(7),
		LONG(8);
		public int type;
		private TradePayType(int type) {
			this.type = type;
		}
	}
	public enum MessageType{
		REGISTER(1),
		UPDATE_PASSWORD( 2),
		FIND_PASSWORD ( 3), 
		LOGIN (4),
		STALL_LOCK_ACCREDIT (5),
		CANCEL_ACCREDIT(6),
		WECHAT_LOGIN(7),
		SEND_COUPON(8),
		SUSPEND_ORDER(9);
		public int type;
		private MessageType(int type) {
			this.type = type;
		}
	}
	
	public enum OrderPayType{
		FREE(1),COUPON(2),ACCOUNT(3);
		public int type;
		private OrderPayType(int type) {
			this.type = type;
		}
	} 
	public enum PrefectureStatus{
		ONLINE(0),OFFLINE(1);
		public int status;
		private PrefectureStatus(int status) {
			this.status = status;
		}
	}
	
	public enum PrefectureCategory{
		COMMON(0),TEST(1),ENTERPRISE(2);
		public int category;
		private PrefectureCategory(int category) {
			this.category = category;
		}
	}
	public enum StallStatus{
		FREE(1),USED(2),OUTLINE(4);
		public int status;
		private StallStatus(int status) {
			this.status = status;
		}
	}
	public enum StallAssignStatus{
		FREE(0),CANCEL(1),ORDER(2);
		public int status;
		private StallAssignStatus(int status) {
			this.status = status;
		}
	}
	
	public enum ThirdPayType{
		WECHAT(1),ALIPAY(2),APPLEPAY(3);
		public int type;
		private ThirdPayType(int type) {
			this.type = type;
		} 
	}
	
	
	
	public enum CouponStatus {
		FREE(0), USED(1), EXPIRED(2);
		public int status;

		private CouponStatus(int status) {
			this.status = status;
		}
	}

	public enum CouponType {
		NORMAL(0), CONDITION(1), DISCOUNT(2);
		public int type;

		private CouponType(int type) {
			this.type = type;
		}
	}
	
	public enum SwitchResult{
		FAILED(0),SUCCESS(1),CLOSED(2);
		public int value;
		private SwitchResult(int value) {
			this.value = value;
		}
	}

	public enum ExpiredTime {
		ACCESS_TOKEN_EXP_TIME(60 * 60 * 24 * 30 * 4), 
		SMS_CODE_SEND_SPACE(60), 
		SMS_CODE_EXP_TIME(600),
		STALL_LOCK_BOOKING_EXP_TIME(60),
		STALL_DOWN_FAIL_EXP_TIME(60 * 60),
		STALL_ORDER_CLOSED_TIME(60*60*24),
		ORDER_SWITCH_RESULT_TIME(60*3),
		WECHAT_TOKEN_EXPIRE(3600),
		WECHAT_GET_USER_LIST_EXPIRE(500),
		COUPON_SEND_COUNT_EXP_TIME(60 * 60 * 24);
		public int time;

		private ExpiredTime(int time) {
			this.time = time;
		}
	}

	public enum ClientSource {
		WXAPP(0), ANDROID(1), IOS(2),APPLET(3);
		public int source;

		private ClientSource(int source) {
			this.source = source;
		}
	}
	
	public enum OrderFailureReason{
		NONE( 0),EXCEPTION (1),	STALL_NONE( 2),	STALL_EXCEPTION( 3),STALL_ORDERED ( 4),	CARNO_BUSY ( 5),USER_LIMIT ( 6),UNPAID(7),CARNO_NONE(8);
		public int value;
		private OrderFailureReason(int value) {
			this.value = value;
		}
	}

	public enum OrderStatus {
		UNPAID(1, "未支付"), PAID(2, "已支付"), COMPLETED(3, "已完成"), CANCELLED(4, "已取消"), SUSPENDED(6, "已挂起"), CLOSED(7,
				"已关闭");
		public int value;
		public String label;

		private OrderStatus(int value, String label) {
			this.value = value;
			this.label = label;
		}
	}
	
	public enum DownLockStatus{
		NONE(0),SUCCESS(1),FAILURE(2);
		public int status;
		private DownLockStatus(int status) {
			this.status = status;
		}
		
	}

	public enum OrderStatusHistory {
		SUSPENDED(1, "挂起"), CLOSED(2, "关闭");
		public int code;
		public String label;

		private OrderStatusHistory(int code, String label) {
			this.code = code;
			this.label = label;
		}
	}

	public enum BindOrderStatus {
		FREE(0, "正常"), SUSPENDED(1, "挂起"), CLOSED(2, "关闭");
		public int status;
		public String label;

		private BindOrderStatus(int status, String label) {
			this.status = status;
			this.label = label;
		}
	}

	public enum OrderOperation {
		SUSPENDED(1, "挂起"), CLOSED(2, "关闭");
		public int code;
		public String label;

		private OrderOperation(int code, String label) {
			this.code = code;
			this.label = label;
		}
	}

	public enum OperateStatus {
		FAILURE(0, "失败"), SUCCESS(1, "成功");
		public int status;
		public String label;

		private OperateStatus(int status, String label) {
			this.status = status;
			this.label = label;
		}
	}
	public enum LockStatus{
		DOWN(0),UP(1),DOWNING(2),UPING(3); 
		public int status;
		private LockStatus(int status) {
			this.status = status;
		}
	}

	public enum OperateSource {
		OPS(1, "后台操作"), STAFF_APP(2, "管理版APP"), USER_APP(3, "个人版APP");
		public int source;
		public String label;

		private OperateSource(int source, String label) {
			this.source = source;
			this.label = label;
		}
	}

	public enum SmsTemplate {
		USER_APP_LOGIN_CODE(1, "user_app_login_code"), 
		SHARE_COUPON_NOTICE(2,"share_coupon_notice"), 
		ORDER_SUSPEND_NOTICE(3,"order_suspend_notice"),  
		ORDER_SEARCH_NOTICE_(4, "order_search_notice"),
		ORDER_CLOSED_NOTICE(5,"order_closed_coupon_notice"),
		BRAND_USER_INVITE_NOTICE(6,"subed_brand_coupon_notice"),
		UN_BRAND_USER_INVITE_NOTICE(7,"unsub_brand_coupon_notice");
		public int type;
		public String id;

		private SmsTemplate(int type, String id) {
			this.id = id;
			this.type = type;
		}
	}

	public enum PushType {
		USER_APP_LOGOUT_NOTICE(0, "USER_APP_LOGOUT_NOTICE"),
		STAFF_ENT_LOGOUT_NOTICE(0, "STAFF_ENT_LOGOUT_NOTICE"),
		STAFF_STAFF_LOGOUT_NOTICE(0, "STAFF_STAFF_LOGOUT_NOTICE"),
		ORDER_CREATE_NOTICE(1,"ORDER_CREATE_NOTICE"),
		LOCK_DOWN_NOTICE(2, "LOCK_DOWN_NOTICE"),
		ORDER_SWITCH_RESULT_NOTICE(3,"ORDER_SWITCH_RESULT_NOTICE"),
		ORDER_SWITCH_STATUS_NOTICE(4,"ORDER_SWITCH_STATUS_NOTICE"),
		ORDER_COMPLETE_NOTICE(5,"ORDER_COMPLETE_NOTICE"),
		OPS_MESSAGE_NOTICE(6,"OPS_MESSAGE_NOTICE"),
		ORDER_AUTO_CLOSE_NOTICE(7,"ORDER_AUTO_CLOSE_NOTICE"),
		ORDER_STAFF_CLOSED_NOTICE(8,"ORDER_STAFF_CLOSED_NOTICE"),
		ORDER_STAFF_SUSPEND_NOTICE(9,"ORDER_STAFF_SUSPEND_NOTICE"),
		LOCK_CONTROL_NOTICE(10,"LOCK_CONTROL_NOTICE");
		public int type;
		public String id; 
		private PushType(int type, String id) {
			this.id = id;
			this.type = type;
		}
	}

	public enum RedisKey {
		USER_WXAPP_AUTH_TOKEN("user_wxapp:auth:openid:"),
		STAFF_WXAPP_AUTH_TOKEN("staff_wxapp:auth:openid:"),
		STAFF_STAFF_WXAPP_AUTH_TOKEN("staff_staff_wxapp:auth:openid:"),
		USER_APP_AUTH_TOKEN("user_app:auth:uid:"), 
		USER_APP_AUTH_USER("user_app:auth:user:"), 
		USER_APP_AUTH_CODE("user_app:auth:code:"), 
		USER_APP_AUTH_MOBILE("user_app:auth:mobile:"), 
		STAFF_ENT_AUTH_TOKEN("staff_ent:auth:uid:"), 
		STAFF_STAFF_AUTH_TOKEN("staff_staff:auth:uid:"), 
		STALL_LOCK_OPER_STATUS("STALL:LOCK:OPER:STATUS:"), 
		STAFF_ENT_AUTH_USER("staff_ent:auth:user:"), 
		STAFF_STAFF_AUTH_USER("staff_staff:auth:user:"), 
		STAFF_ENT_AUTH_CODE("staff_ent:auth:code:"), 
		STAFF_STAFF_AUTH_CODE("staff_staff:auth:code:"), 
		STAFF_ENT_AUTH_MOBILE("staff_ent:auth:mobile:"), 
		STAFF_STAFF_AUTH_MOBILE("staff_staff:auth:mobile:"), 
		USER_APP_USER_CODE("user_app:user:code:"), 
		USER_APP_USER_MOBILE("user_app:user:mobile:"), 
		USER_APP_USER_CHANGE_MOBILE("user_app:user:change-mobile:"), 
		USER_APP_BRAND_COUPON("user_app:brand:coupon:"),
		COMMON_CAR_BRAND_LIST("common:car-brand:"), 
		COUPON_TEMPLATE_CONDITION_USETIME("COUPON_TEMP_CONDITION_USETIME:"), 
		COUPON_TEMPLATE_CONDITION_PREIDS( "COUPON_TEMP_CONDITION_PREIDS:"),
		COUPON_SEND_RECORD_MOBILE( "COUPON_SEND_RECORD_MOBILE:"),
		ORDER_ASSIGN_STALL("assign_lock"),
		ORDER_SERIAL_NUMBER("order:order:serial_number:"),
		ORDER_TRADE_SERIAL_NUMBER("order:trade:serial_number:"),
		ORDER_RECHARGE_SERIAL_NUMBER("order:recharge:serial_number:"),
		ORDER_STALL_DOWN_FAILED("order:lock-down:failure:"),
		ACTION_STALL_DOWN_FAILED("action:lock-down:failure:"),
		ACTION_STALL_DOING("action:lock-doing"),
		ROB_STALL_ISHAVE("rob:stall-ishave"),
		ENT_STALL_DOING("action:ent-lock-doing:failure:"),
		ACTION_STALL_UP_FAILED("action:lock-up:failure:"),
		STALL_ORDER_CLOSED("stall:order-closed:"),
		ORDER_SWITCH_STALL_FAILED_COUNT("order:switch-stall-failure:count:"),
		ORDER_SWITCH_RESULT("order:switch-result:"),
		PREFECTURE_FREE_STALL("freelock_key:"),
		PREFECTURE_BRAND_FREE_STALL("prefecture:brand-free-stall:"),
		PREFECTURE_BUSY_STALL("prefecture:busy-stall:"),
//		WECHAT_TOKEN_KEY("wechat:token"),
		WECHAT_TOKEN_KEY_MONITOR("monitor:wechat_token:"),
		WECHAT_GET_USER_LIST_MONITOR("monitor:wechat-user-list:"), 
		USER_GROUP_IDS("USER_GROUP_IDS:"),
		BLACKLIST_ORDER_LIMIT("blacklist:order:limit"),
		
		 LINKMORE_APP_ORDER_KEY("LINKMORE_APP_ORDER_"),
		
		MANAGER_STALL_UP("manager:lock-uping:"),
		MANAGER_STALL_DOWN("manager:lock-downing:"),
		MANAGER_STALL("manager:lock:");
		
		public String key;

		private RedisKey(String key) {
			this.key = key;
		}
	}
}
