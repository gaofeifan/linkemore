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
	
	public enum TradePayType{
		ACCOUNT(0),
		ALIPAY( 1),
		WECHAT ( 2), 
		APPLE (3);
		public int type;
		private TradePayType(int type) {
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

	public enum ExpiredTime {
		ACCESS_TOKEN_EXP_TIME(60 * 60 * 24 * 30 * 4), SMS_CODE_SEND_SPACE(60), SMS_CODE_EXP_TIME(600);
		public int time;

		private ExpiredTime(int time) {
			this.time = time;
		}
	}

	public enum ClientSource {
		WXAPP(0), ANDROID(1), IOS(2);
		public int source;

		private ClientSource(int source) {
			this.source = source;
		}
	}
	
	public enum OrderFailureReason{
		NONE( 0),EXCEPTION (1),	STALL_NONE( 2),	STALL_EXCEPTION( 3),STALL_ORDERED ( 4),	CARNO_BUSY ( 5),USER_LIMIT ( 6),UNPAID(7);
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
		UP(1),DOWN(2); 
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
		USER_APP_LOGIN_CODE(1, "user_app_login_code"), SHARE_COUPON_NOTICE(2,
				"share_coupon_notice"), ORDER_SUSPEND_NOTICE(3,
						"order_suspend_notice"), ORDER_SEARCH_NOTICE_(4, "order_search_notice");
		public int type;
		public String id;

		private SmsTemplate(int type, String id) {
			this.id = id;
			this.type = type;
		}
	}

	public enum PushType {
		USER_APP_LOGOUT_NOTICE(0, "USER_APP_LOGOUT_NOTICE"), 
		ORDER_CREATE_NOTICE(1,"ORDER_CREATE_NOTICE"), 
		LOCK_DOWN_NOTICE(2, "LOCK_DOWN_NOTICE"),
		ORDER_SWITCH_RESULT_NOTICE(3,"ORDER_SWITCH_RESULT_NOTICE"),
		ORDER_SWITCH_STATUS_NOTICE(4,"ORDER_SWITCH_STATUS_NOTICE"),
		ORDER_COMPLETE_NOTICE(5,"ORDER_COMPLETE_NOTICE");
		public int type;
		public String id; 
		private PushType(int type, String id) {
			this.id = id;
			this.type = type;
		}
	}

	public enum RedisKey {
		USER_APP_AUTH_TOKEN("user_app:auth:uid:"), 
		USER_APP_AUTH_USER("user_app:auth:user:"), 
		USER_APP_AUTH_CODE("user_app:auth:code:"), 
		USER_APP_AUTH_MOBILE("user_app:auth:mobile:"), 
		USER_APP_USER_CODE("user_app:user:code:"), 
		USER_APP_USER_MOBILE("user_app:user:mobile:"), 
		COMMON_CAR_BRAND_LIST("common:car_brand:list"), 
		COUPON_TEMPLATE_CONDITION_USETIME("COUPON_TEMP_CONDITION_USETIME:"), 
		COUPON_TEMPLATE_CONDITION_PREIDS( "COUPON_TEMP_CONDITION_PREIDS:"),
		COUPON_SEND_RECORD_MOBILE( "COUPON_SEND_RECORD_MOBILE:"),
		ORDER_ASSIGN_STALL("assign_lock"),
		ORDER_SERIAL_NUMBER("order:order:serial_number:"),
		ORDER_TRADE_SERIAL_NUMBER("order:trade:serial_number:"),
		ORDER_RECHARGE_SERIAL_NUMBER("order:recharge:serial_number:"),
		ORDER_STALL_DOWN_FAILED("order:lock-down:failure:"),
		PREFECTURE_FREE_STALL("prefecture:free_stall:"),
		WECHAT_TOKEN_KEY("wechat_token"),
		WECHAT_TOKEN_EXPIRE("3600"),
		BLACKLIST_ORDER_LIMIT("blacklist:order:limit");
		public String key;

		private RedisKey(String key) {
			this.key = key;
		}
	}
}
