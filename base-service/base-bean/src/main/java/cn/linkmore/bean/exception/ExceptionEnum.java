package cn.linkmore.bean.exception;
/**
 * Enum - 异常类型
 * @author liwenlong
 * @version 2.0
 *
 */
public enum ExceptionEnum {
	SUCCESS(200, "操作成功"),   
	BAD_REQUEST(400, "错误的请求"),  
	NO_SERVICE(404, "服务不存在"),  
	UNAUTHORIZED(403, "未授权访问"),  
	SERVER_EXCEPTION(500, "服务异常"), 
	VALID_EXCEPTION(501, "请求参数不合法"); 
	
	public int code;
	public String label;

	private ExceptionEnum(int code, String label) {
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
		for (ExceptionEnum status : ExceptionEnum.values()) {
			if (status.code == code) {
				result = status.label;
			}
		}
		return result;
	}
}
