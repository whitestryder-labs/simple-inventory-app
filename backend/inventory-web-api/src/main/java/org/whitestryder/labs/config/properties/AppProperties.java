package org.whitestryder.labs.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * Encapsulates the custom application properties.
 * 
 * NOTE: I think this is better than littering the code with @Value annotations everywhere
 * and besides, @Value annotation default values don't make sense when you need to use a
 * property from multiple places because you would need to ensure the default values were
 * specified in both places correctly.  Better to have a central object for app properties
 * that can be autowired as an object.
 * 
 *  The convention is that private field "foo" in this class can be set externally as:
 *  
 *   app.property.foo = ...
 *   
 */
@Component
@ConfigurationProperties(prefix="app.property")
public class AppProperties {

	/** The token TTL seconds. */
	private int tokenTTLSeconds = 600;	//default 10 minute TTL, <= 0 means it will never expire
	
	/**
	 * Gets the token TTL seconds.
	 *
	 * @return the token TTL seconds
	 */
	public int getTokenTTLSeconds() {
		return tokenTTLSeconds;
	}

	/**
	 * Sets the token TTL seconds.
	 *
	 * @param tokenTTLSeconds the new token TTL seconds
	 */
	public void setTokenTTLSeconds(int tokenTTLSeconds) {
		this.tokenTTLSeconds = tokenTTLSeconds;
	}
	
	
}
