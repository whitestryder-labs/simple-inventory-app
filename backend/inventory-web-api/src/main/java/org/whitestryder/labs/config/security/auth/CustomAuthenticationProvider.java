package org.whitestryder.labs.config.security.auth;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	
	private UserDetailsService userDetailsService;
	
	public CustomAuthenticationProvider(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
  
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

         UserDetails details = userDetailsService.loadUserByUsername(name);
         if (details != null && password.equals(details.getPassword())){
            // use the credentials
            // and authenticate against the third-party system
            return new UsernamePasswordAuthentication(
            		details.getUsername(), details.getPassword());
        } else {
            return null;
        }
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
        		UsernamePasswordAuthentication.class);
    }

}
