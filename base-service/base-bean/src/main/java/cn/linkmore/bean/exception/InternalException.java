package cn.linkmore.bean.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.netflix.hystrix.exception.HystrixBadRequestException;

public class InternalException   extends HystrixBadRequestException {
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 8815991511079281647L;

	@JsonIgnoreProperties
	public InternalException(String message) {
		super(message);
	} 
}
