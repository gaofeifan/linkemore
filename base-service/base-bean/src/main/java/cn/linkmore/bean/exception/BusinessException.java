package cn.linkmore.bean.exception;
/**
 * Exception - 全局业务异常
 * @author liwenlong
 * @version 2.0
 *
 */
public class BusinessException extends RuntimeException{
	private static final long serialVersionUID = -4310721413425427596L;
	/**
	 * 错误码
	 */
	private Integer code = StatusEnum.SERVER_EXCEPTION.code;
	/**
	 * 错误码的说明
	 */
	private String label = StatusEnum.SERVER_EXCEPTION.label;
	
	private StatusEnum statusEnum;

	public BusinessException() {
		super();
	}

	/**
	 * 构造函数
	 * 
	 * @param code：错误码
	 * @param codeLabel：错误码说明
	 */
	public BusinessException(int code, String codeLabel) {
		super(codeLabel);
		setCode(code);
		setLabel(codeLabel);
	}

	/**
	 * 构造函数
	 * 
	 * @param code：错误码
	 * @param codeLabel：错误码说明
	 * @param message：异常说明
	 */
	public BusinessException(int code, String codeLabel, String message) {
		super(message);
		setCode(code);
		setLabel(codeLabel);
	}

	/**
	 * 构造函数
	 * 
	 * @param code：错误码
	 * @param codeLabel：错误码说明
	 * @param message：异常说明
	 * @param cause
	 */
	public BusinessException(int code, String codeLabel, String message, Throwable cause) {
		super(message, cause);
		setCode(code);
		setLabel(codeLabel);
	}

	/**
	 * 构造函数
	 * 
	 * @param error：错误码
	 */
	public BusinessException(StatusEnum error) {
		super(error.label);
		setCode(error.code);
		setLabel(error.label);
		setStatusEnum(error);
	}

	/**
	 * 构造函数
	 * 
	 * @param error：错误码
	 * @param message：异常说明
	 */
	public BusinessException(StatusEnum error, String message) {
		super(message);
		setCode(error.code);
		setLabel(error.label);
		setStatusEnum(error);
	}

	/**
	 * 构造函数
	 * 
	 * @param error：错误码
	 * @param message：异常说明
	 * @param cause
	 */
	public BusinessException(StatusEnum error, String message, Throwable cause) {
		super(message, cause);
		setCode(error.code);
		setLabel(error.label);
		setStatusEnum(error);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	} 

	public StatusEnum getStatusEnum() {
		return statusEnum;
	}

	public void setStatusEnum(StatusEnum statusEnum) {
		this.statusEnum = statusEnum;
	} 
}
