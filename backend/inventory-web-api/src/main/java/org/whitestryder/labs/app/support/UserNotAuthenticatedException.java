package org.whitestryder.labs.app.support;



/**
 * Provides a way to indicate that the user is not authenticated.
 * @author steve
 *
 */
public class UserNotAuthenticatedException extends ApplicationException {

	/**
	 * generated
	 */
	private static final long serialVersionUID = -5210309263053566629L;

	public UserNotAuthenticatedException() {
		super();
	}

	public UserNotAuthenticatedException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public UserNotAuthenticatedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UserNotAuthenticatedException(String arg0) {
		super(arg0);
	}

	public UserNotAuthenticatedException(Throwable arg0) {
		super(arg0);
	}
	
}
