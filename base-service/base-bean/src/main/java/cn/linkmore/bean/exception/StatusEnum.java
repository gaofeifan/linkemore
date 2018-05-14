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
	
	USER_APP_NO_LOGIN(2000, "请求参数不合法"), 
	USER_APP_ILLEGAL_REQUEST(2001,"非法请求"), 
	USER_APP_SMS_FAILED(2002,"短信发送失败"),
	USER_APP_SMS_ERROR(2003,"验证码错误"),
	USER_APP_SMS_EXPIRED(2003,"验证码已过期");
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
