package cn.linkmore.bean.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class InternalException extends RuntimeException implements BaseException {
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 8815991511079281647L;

	@JsonIgnoreProperties
	public InternalException(String message) {
		super(message);
	}

	@Override
	@JsonIgnoreProperties
	public Exception getException() {
		return this; 
	}
}
