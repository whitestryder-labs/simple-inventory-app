package org.whitestryder.labs.api.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

	/**
	 * generated
	 */
	private static final long serialVersionUID = -3692626392616699621L;
	
	
	private boolean authenticated;
	private String username;

	public AuthenticationResponse() {
		super();
	}
	
	public AuthenticationResponse(boolean authenticated) {
		super();
		this.authenticated = authenticated;
		this.username = null;
	}

	public AuthenticationResponse(boolean authenticated, String username) {
		super();
		this.authenticated = authenticated;
		this.username = username;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public String getUsername() {
		return username;
	}

	
}
