package org.whitestryder.labs.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.whitestryder.labs.config.security.auth.CustomAuthenticationProvider;
import org.whitestryder.labs.config.security.auth.EntryPointUnauthorizedHandler;
import org.whitestryder.labs.config.security.auth.UserService;

/**
 * Defined bean implementations for dependency injection
 * @author steve
 *
 */
@Configuration
@Order(1)
public class Config {
	
	@Bean
	public UserService userService(){
		UserService service = new UserService();
		
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		User user1 = new User("sbrooke", "password", grantedAuthorities);
		User user2 = new User("amagi", "password", grantedAuthorities);
		service.addUser(user1);
		service.addUser(user2);
		
		return service;
	}
	
	@Bean
	public EntryPointUnauthorizedHandler unauthorizedHandler(){
		return new EntryPointUnauthorizedHandler();
	}
	
	@Bean
	public AuthenticationProvider authProvider(UserService userService){
		CustomAuthenticationProvider provider = new CustomAuthenticationProvider(userService);
		return provider;
	}
}
