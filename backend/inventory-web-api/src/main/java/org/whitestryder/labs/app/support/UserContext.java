package org.whitestryder.labs.app.support;


/**
 * Holder for information about the currently authenticated user.
 */
public class UserContext {

	/** The username. */
	private String username;
	
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}


	/**
	 * Instantiates a new user context.
	 *
	 * @param username the username
	 */
	public UserContext(String username) {
		this.username = username;
	}

}
