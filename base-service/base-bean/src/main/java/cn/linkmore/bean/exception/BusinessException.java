package cn.linkmore.bean.exception;
/**
 * Exception - 全局业务异常
 * @author liwenlong
 * @version 2.0
 *
 */
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -4310721413425427596L;
	/**
	 * 错误码
	 */
	private Integer code = ExceptionEnum.SERVER_EXCEPTION.code;
	/**
	 * 错误码的说明
	 */
	private String label = ExceptionEnum.SERVER_EXCEPTION.label;
	
	private ExceptionEnum exceptionEnum;

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
	public BusinessException(ExceptionEnum error) {
		super(error.label);
		setCode(error.code);
		setLabel(error.label);
		setExceptionEnum(error);
	}

	/**
	 * 构造函数
	 * 
	 * @param error：错误码
	 * @param message：异常说明
	 */
	public BusinessException(ExceptionEnum error, String message) {
		super(message);
		setCode(error.code);
		setLabel(error.label);
		setExceptionEnum(error);
	}

	/**
	 * 构造函数
	 * 
	 * @param error：错误码
	 * @param message：异常说明
	 * @param cause
	 */
	public BusinessException(ExceptionEnum error, String message, Throwable cause) {
		super(message, cause);
		setCode(error.code);
		setLabel(error.label);
		setExceptionEnum(error);
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

	public ExceptionEnum getExceptionEnum() {
		return exceptionEnum;
	}

	public void setExceptionEnum(ExceptionEnum exceptionEnum) {
		this.exceptionEnum = exceptionEnum;
	}
	
}
