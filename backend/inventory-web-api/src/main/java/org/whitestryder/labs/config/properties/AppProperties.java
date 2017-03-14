package org.whitestryder.labs.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


// TODO: Auto-generated Javadoc
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
	
	/** The surge price model access time mins. */
	private int surgePriceModelAccessTimeMins = 60;
	
	/** The surge price model views per access time threshold. */
	private int surgePriceModelViewsPerAccessTimeThreshold = 10;
	
	/** The surge price model price increase percent. */
	private double surgePriceModelPriceIncreasePercent = 0.10; 	//10% 
	
	
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

	/**
	 * Gets the surge price model access time mins.
	 *
	 * @return the surge price model access time mins
	 */
	public int getSurgePriceModelAccessTimeMins() {
		return surgePriceModelAccessTimeMins;
	}

	/**
	 * Sets the surge price model access time mins.
	 *
	 * @param surgePriceModelAccessTimeMins the new surge price model access time mins
	 */
	public void setSurgePriceModelAccessTimeMins(int surgePriceModelAccessTimeMins) {
		this.surgePriceModelAccessTimeMins = surgePriceModelAccessTimeMins;
	}

	/**
	 * Gets the surge price model views per access time threshold.
	 *
	 * @return the surge price model views per access time threshold
	 */
	public int getSurgePriceModelViewsPerAccessTimeThreshold() {
		return surgePriceModelViewsPerAccessTimeThreshold;
	}

	/**
	 * Sets the surge price model views per access time threshold.
	 *
	 * @param surgePriceModelViewsPerAccessTimeThreshold the new surge price model views per access time threshold
	 */
	public void setSurgePriceModelViewsPerAccessTimeThreshold(int surgePriceModelViewsPerAccessTimeThreshold) {
		this.surgePriceModelViewsPerAccessTimeThreshold = surgePriceModelViewsPerAccessTimeThreshold;
	}

	/**
	 * Gets the surge price model price increase percent.
	 *
	 * @return the surge price model price increase percent
	 */
	public double getSurgePriceModelPriceIncreasePercent() {
		return surgePriceModelPriceIncreasePercent;
	}

	/**
	 * Sets the surge price model price increase percent.
	 *
	 * @param surgePriceModelPriceIncreasePercent the new surge price model price increase percent
	 */
	public void setSurgePriceModelPriceIncreasePercent(double surgePriceModelPriceIncreasePercent) {
		this.surgePriceModelPriceIncreasePercent = surgePriceModelPriceIncreasePercent;
	}
	
	
	
}
