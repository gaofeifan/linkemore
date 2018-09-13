package cn.linkmore.enterprise.entity;

import cn.linkmore.bean.exception.StatusEnum;

/**
 * 错误异常信息类
 * @author liwl
 * @version 1.0
 *
 */
public enum Msg {
	
	SUCCESS(200, "操作成功"),   
	APP_BAD_REQUEST(400, "错误的请求"),  
	APP_NOSERVICE(404, "服务不存在"),  
	APP_UNAUTHORIZED(403, "未授权访问"), 
	
	APP_EXCEPTION(500, "服务异常"), 
	APP_VALID_EXCEPTION(501, "请求参数不合法"), 
	
	USER_NOT_LOGIN(210,"用户未登录"),
	USER_LOCKED(211,"账户被锁"),
	USER_NONE(212,"账户不存在"), 
	 
	
	SMS_SEND_ERROR(230,"发送失败"),
	SMS_CODE_EXPIRE(231,"短信验证码已过期"),
	SMS_CODE_ERROR(232,"短信验证码错误"),
	RELEASE_CODE_ERROR(233,"车位释放失败"), 
	FAIL(251,"操作失败"),
	
	ORDER_OPERATE_NULLSTALL(300,"车位不存在"),
	ORDER_OPERATE_NULLORDER(301,"订单不存在"),
	ORDER_OPERATE_NOUNPAID(302,"非预约中订单"),
	
	STALL_OPERATE_OFFLINED(310,"车位已经下线"),
	STALL_OPERATE_ONLINED(311,"车位已经上线"),
	STALL_OPERATE_LOCKDOWN(312,"车位地锁未升起"),
	STALL_OPERATE_UNOFFLINE(313,"车不在下线状态"),
	STALL_LOCK_OFFLINE(314,"车位锁通信失败"),
	STALL_OPERATE_ORDERING(315,"车位有预约订单"),
	
	COUPON_SEND_ERR(320,"停车券发放失败，请联系管理员"),
	STALL_OPERATE_ASSIGN(331,"指定车位失败"),
	STALL_OPERATE_ASSIGN_DELETE(332,"删除指定车位失败"),
	
	CUSTOMER_SUBMIT_ERR(330,"信息保存失败")
	
	;
	public int code;
	public String label;

	private Msg(int code, String label) {
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
		for (Msg status : Msg.values()) {
			if (status.code == code) {
				result = status.label;
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
