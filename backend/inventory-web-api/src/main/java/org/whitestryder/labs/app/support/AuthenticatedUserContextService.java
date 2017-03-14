package org.whitestryder.labs.app.support;

// TODO: Auto-generated Javadoc
/**
 * Provides a way to get the currently authenticated user.
 */
public interface AuthenticatedUserContextService {

	/**
	 * Gets the authenticated user.
	 *
	 * @return the authenticated user
	 * @throws UserNotAuthenticatedException the user not authenticated exception
	 */
	UserContext getAuthenticatedUser() throws UserNotAuthenticatedException;
	
}
