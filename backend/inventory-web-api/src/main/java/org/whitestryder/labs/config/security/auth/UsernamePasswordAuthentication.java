package org.whitestryder.labs.config.security.auth;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;

public class UsernamePasswordAuthentication extends UserAuthentication {

	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 6776088355411842586L;

	
	
	public UsernamePasswordAuthentication(String username, String password){
		super(new User(username, password, new ArrayList<>()));
	}
}
