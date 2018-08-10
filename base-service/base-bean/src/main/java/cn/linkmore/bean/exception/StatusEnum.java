package cn.linkmore.bean.exception;
/**
 * Enum - 异常类型
 * @author liwenlong
 * @version 2.0
 *
 */
public enum StatusEnum {
	SUCCESS(200, "操作成功"),   
	BAD_REQUEST(400, "错误的请求"),  
	NO_SERVICE(404, "服务不存在"),  
	UNAUTHORIZED(403, "未授权访问"),  
	SERVER_EXCEPTION(500, "服务异常"), 
	VALID_EXCEPTION(501, "请求参数不合法"), 
	
	USER_APP_NO_LOGIN(9002000, "用户未登录"), 
	USER_APP_ILLEGAL_REQUEST(9002001,"非法请求"), 
	USER_APP_SMS_FAILED(9002002,"短信发送失败"),
	USER_APP_SMS_ERROR(9002003,"验证码错误"),
	USER_APP_SMS_EXPIRED(9002003,"验证码已过期"), 
	
	THIRD_FILE_UPLOAD_ERROR(800000,"oss上传文件失败"),
	THIRD_IMAGE_UPLOAD_ERROR(800001,"oss上传图片失败"),
	
	
	COMMON_FILE_UPLOAD_ERROR(800100,"上传文件失败"),
	COMMON_IMAGE_UPLOAD_ERROR(800101,"上传图片失败"),
	
	ACCOUNT_USER_NOT_EXIST(8003000 ,"用户不存在"),
	ACCOUNT_USER_MOBILE_EXIST(8003001 ,"手机号已被占用"),  
	ACCOUNT_WECHAT_LOGIN_ERROR(8003002,"微信用户登录失败"), 
	ACCOUNT_USER_LOCKED(8003003 ,"账户被锁"),
	ACCOUNT_WECHAT_BINDING_ERROR(8003004,"微信已被绑定"), 
	ACCOUNT_WECHAT_BINDING_NOMOBILE(800305,"未绑定手机号"),
	ACCOUNT_USER_CHANGE_MOBILE(800306,"30天内无法更换手机"),
	
	ACCOUNT_PLATE_LIMIT(8003021,"添加失败,最多添加三个!"), 
	ACCOUNT_PLATE_EXISTS(8003022,"添加失败,车牌号已存在! "), 
	ACCOUNT_PLATE_NONE(8003023 ,"删除失败,车牌号不存在 "), 
	
	COUPON_USER_NO_EQUIRE(8004001,"非当前用户停车券"),
	
	
	ORDER_UNPAY_ORDER(8005001,"您有未支付的订单，请先结账!"),
	ORDER_CREATE_FAIL(8005002,"预约失败请重新预约^_^!"),
	ORDER_USERID_ERROR(8005003,"取消失败,预约用户信息错误!"),
	ORDER_WAS_PENDING(8005004,"取消失败,挂起订单不能被取消!"),
	ORDER_STATUS_EXPIRE(8005005,"取消失败,该订单已结束!"),
	ORDER_LOCK_DOWN(8005006,"取消失败,降下地锁不能取消订单!"),
	ORDER_CANCEL_TIMEOUT(8005007,"取消失败,订单超时!"),
	ORDER_CANCEL_MORETIMES(8005008,"您取消预约次数超限无法取消!"),
	ORDER_CANCEL_FAILED(8005009,"取消失败!"),
	ORDER_CHECK_EXPIRE(8005010,"结算失败,订单不存在或订单已过期!"),
	ORDER_CHECK_YL_ORDER(8005011,"银联订单请到银联平台完成相关操作"),
	ORDER_PAY_UNKNOW_RECHARGE(8005012,"无效的支付数据"),
	ORDER_PAY_UNPAY(8005013,"订单未支付"),
	ORDER_PAY_SIGN_ERROR(8005014,"订单签名失败"),
	ORDER_UNKNOW_PAY(8005015,"未知支付类型"),
	ORDER_PAY_ACCOUNT_AMOUNT_LOW(8005016,"账户余额不足"),
	ORDER_PAY_FAIL(8005017,"支付失败!"),
	ORDER_WXPAY_FAIL(8005018,"微信支付失败!"),
	
	ORDER_RECHARGE_PAY_FAIL(8005019,"支付失败"),
	ORDER_RECHARGE_WXPAY_FAIL(8005020,"微信支付失败"),
	ORDER_RECHARGE_WXPAY_VERIFY_ERROR(8005021,"微信支付订单校验失败"),
	ORDER_RECHARGE_ALIPAY_FAIL(8005022,"支付宝支付失败"),
	ORDER_RECHARGE_ALIPAY_VERIFY_ERROR(8005023,"支付宝订单校验失败"),
	
	//降锁失败
	ORDER_LOCKDOWN_FAIL(8005024,"降下地锁失败！"),
	ORDER_FAIL_SWITCHLOCK(8005025,"若降锁失败,请切换其他车位"),
	ORDER_FAIL_CHECKOUT(8005026,"若反复尝试降锁失败,请点击结账离场;若产生费用,请联系客服"),
	ORDER_FAIL_CLOSEORDER(8005027,"反复尝试降锁失败,当前设备可能有故障请关闭订单"),
	ORDER_LOCKDOWN_UNPAY(8005028,"非预约订单无法降锁"),
	
	ORDER_RECHARGE_PAY_TYPE_NULL(8005029,"不支持的支付方式"),
	ORDER_RECHARGE_SIGNATURE_ERROR(8005030,"签名订单出错"), 
	ORDER_RECHARGE_AMOUNT_ERROR(8005031,"充值面额错误"), 
	 
	ORDER_REASON_EXCEPTION(8005032,"系统异常，请重新预约"),
	ORDER_REASON_STALL_NONE(8005033,"无空闲车位，请重新预约"),
	ORDER_REASON_STALL_EXCEPTION(8005034,"无空闲车位，请重新预约"),
	ORDER_REASON_STALL_ORDERED(8005035,"车位已占用，请重新预约"),
	ORDER_REASON_CARNO_BUSY(8005036,"当前车牌号已在预约中，请更换车牌号重新预约"),
	ORDER_REASON_USER_LIMIT(8005037,"车牌不存在"),
	ORDER_REASON_CARNO_NONE(8005038,"无空闲车位，请重新预约"),
	ORDER_REASON_SWITCHSTALL_ORDINARY_FAIL(8005051,"更换车位失败，请点击结账离场；若产生费用，请联系客服"),
	ORDER_REASON_SWITCHSTALL_FAIL(8005052,"切换车位失败"),
	ORDER_LOCKUP_FAIL(8005060,"升起地锁失败！"),
	
	BRAND_APPLICANT_FAIL(8005061,"你已申请过该品牌专享车位资格，请等待审核"),
	BRAND_APPLICANT_ENT_FAIL(8005062,"申请企业信息不存在"),
	BRAND_APPLICANT_ENT_BRAND_FAIL(8005063,"当前企业未投放品牌广告"),
	BRAND_APPLICANT_ENT_BRAND_AD_FAIL(8005064,"今日品牌活动推广已结束"),
	
	STAFF_STALL_EXISTS(8005065,"没有该车位权限");
	
	public int code;
	public String label;

	private StatusEnum(int code, String label) {
		this.code = code;
		this.label = label;
	} 
	/**
	 * 获取状态码描述
	 *
	 * @param code
	 *            状态码
	 * @return 状态码描述，如果没有返回空串
	 */
	public static String getLabel(int code) {
		String result = "";
		for (StatusEnum status : StatusEnum.values()) {
			if (status.code == code) {
				result = status.label;
				break;
			}
		}
		return result;
	}
	
	public static StatusEnum get(int code) {
		StatusEnum se = null;
		for (StatusEnum status : StatusEnum.values()) {
			if (status.code == code) {
				se = status;
				break;
			}
		}
		return se;
	}
}
