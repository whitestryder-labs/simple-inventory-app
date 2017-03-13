package org.whitestryder.labs.app.support;

/**
 * Provides a way to get the currently authenticated user.
 */
public interface AuthenticatedUserContextService {

	UserContext getAuthenticatedUser() throws UserNotAuthenticatedException;
	
}
