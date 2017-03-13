package org.whitestryder.labs.app.support;


/**
 * Provides a way to indicate a general application exception occurred.
 * @author steve
 *
 */
public class ApplicationException extends Exception {

	/**
	 * generated
	 */
	private static final long serialVersionUID = 264215536292357855L;

	public ApplicationException() {
		super();
	}

	public ApplicationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ApplicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApplicationException(String message) {
		super(message);
	}

	public ApplicationException(Throwable cause) {
		super(cause);
	}

	
	
	
}
