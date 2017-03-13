package org.whitestryder.labs.support;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.whitestryder.labs.app.support.AuthenticatedUserContextService;
import org.whitestryder.labs.app.support.UserContext;
import org.whitestryder.labs.app.support.UserNotAuthenticatedException;
import org.whitestryder.labs.config.security.auth.TokenAuthenticationService;
import org.whitestryder.labs.config.security.auth.UserAuthentication;

@Configuration
public class MockWebSecurityTestConfig {

	
	@Bean
	@Primary
	public AuthenticatedUserContextService authUserContextService(){
		return new MockUserContextService();
	}
	
	
	class MockUserContextService implements AuthenticatedUserContextService {

		@Override
		public UserContext getAuthenticatedUser() throws UserNotAuthenticatedException {
			UserContext userContext = new UserContext("testuser");
			return userContext;
		}
		
	}
	

	@Bean
	@Primary
	public AuthenticationManager authManager(){
		return new MockAuthManager();
	}
	
	class MockAuthManager implements AuthenticationManager {

		@Override
		public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	       	Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	    	User user = new User("user", "password", grantedAuthorities);
	    	UserAuthentication mockAuth = new UserAuthentication(user);	    	
			return mockAuth;
		}
		
	}
	
	
	@Bean
	@Primary
	public TokenAuthenticationService authenticationService(AuthenticationManager authManager){
		return new MockAuthenticationService(authManager);
	}


	class MockAuthenticationService implements TokenAuthenticationService {

		private AuthenticationManager authManager;
		
		public MockAuthenticationService(AuthenticationManager authManager) {
			this.authManager = authManager;
		}
		
		
		@Override
		public void addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Authentication getAuthentication(HttpServletRequest request) {
			
	       	Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
	    	User user = new User("user", "password", grantedAuthorities);
	    	UserAuthentication mockAuth = new UserAuthentication(user);
	    	
			return mockAuth;
		}
		
	}
}
