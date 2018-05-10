package cn.linkmore.bean.exception;

public class InternalException extends RuntimeException implements BaseException {
	/**
	 * serial id
	 */
	private static final long serialVersionUID = 8815991511079281647L;

	public InternalException(String message) {
		super(message);
	}

	@Override
	public Exception getException() {
		return this; 
	}
}
