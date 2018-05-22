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
		WXAPP(1), ANDROID(2), IOS(3);
		public int source;

		private ClientSource(int source) {
			this.source = source;
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
		public int code;
		public String label;

		private BindOrderStatus(int code, String label) {
			this.code = code;
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
		USER_APP_LOGOUT_NOTICE(0, "USER_APP_LOGOUT_NOTICE"), ORDER_CREATE_NOTICE(1,
				"ORDER_CREATE_NOTICE"), LOCK_DOWN_NOTICE(1, "LOCK_DOWN_NOTICE");
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
		COUPON_TEMPLATE_CONDITION_USETIME("coupon:temp:condition:time:"), 
		COUPON_TEMPLATE_CONDITION_PREIDS( "coupon:temp:condition:pid:"),
		ORDER_ASSIGN_STALL("assign_lock");
		public String key;

		private RedisKey(String key) {
			this.key = key;
		}
	}
}