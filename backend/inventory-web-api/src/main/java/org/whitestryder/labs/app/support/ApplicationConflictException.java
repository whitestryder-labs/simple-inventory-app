package org.whitestryder.labs.app.support;


/**
 * Provides a way to indicate that an operation has caused an application conflict and the operation cannot proceed.
 * @author steve
 *
 */
public class ApplicationConflictException extends ApplicationException {

	/**
	 * generated
	 */
	private static final long serialVersionUID = -5899003320598306099L;

	public ApplicationConflictException() {
		super();

	}

	public ApplicationConflictException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

	public ApplicationConflictException(String message, Throwable cause) {
		super(message, cause);

	}

	public ApplicationConflictException(String message) {
		super(message);

	}

	public ApplicationConflictException(Throwable cause) {
		super(cause);

	}

	
	
}
