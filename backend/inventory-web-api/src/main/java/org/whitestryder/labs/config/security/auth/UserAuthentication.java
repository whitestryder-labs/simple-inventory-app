package org.whitestryder.labs.config.security.auth;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


/**
 * Encapsulates the concept of an authenticated user. This class implements Spring Security's Authentication interface,
 * which is how Spring ties users, authorities/roles, principals, credentials, and authentication status together.
 * The implementation above always assumes the user is authenticated and delegates the rest to Spring Security's own User class.
 * 
 * @author steve
 *
 */
public class UserAuthentication implements Authentication {

    /**
	 * generated 
	 */
	private static final long serialVersionUID = -5735880827589790027L;
	
	private final User user;
    private boolean authenticated = true;

    public UserAuthentication(User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public User getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}