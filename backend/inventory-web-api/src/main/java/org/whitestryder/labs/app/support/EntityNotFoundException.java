package org.whitestryder.labs.app.support;


/**
 * Provide a way to signal that an entity could not be found.
 * @author steve
 *
 */
public class EntityNotFoundException extends ApplicationException {

	/**
	 * generated
	 */
	private static final long serialVersionUID = -7190107203246773278L;

	
	public EntityNotFoundException() {
		super();
	}

	public EntityNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityNotFoundException(String message) {
		super(message);
	}

	public EntityNotFoundException(Throwable cause) {
		super(cause);
	}
	
	
}
