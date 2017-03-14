package org.whitestryder.labs.config.security.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.whitestryder.labs.app.support.AuthenticatedUserContextService;
import org.whitestryder.labs.app.support.UserContext;
import org.whitestryder.labs.app.support.UserNotAuthenticatedException;

public class AuthenticatedUserContextServiceImpl implements AuthenticatedUserContextService {

	@Override
	public UserContext getAuthenticatedUser() throws UserNotAuthenticatedException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth == null){
			throw new UserNotAuthenticatedException("There is no authenticated user");
		}
		
		if (auth.getName() == "anonymous"){
			throw new UserNotAuthenticatedException("There is no authenticated user, anonymous user cannot be authenticated");
		}
		
		return new UserContext(auth.getName());
	}
	
	
}
