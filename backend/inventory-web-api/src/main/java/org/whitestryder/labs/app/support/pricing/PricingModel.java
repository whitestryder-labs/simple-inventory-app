package org.whitestryder.labs.app.support.pricing;

import java.util.Map;

/**
 * Provide an interface for creating different pricing models
 */
public interface PricingModel {


	/**
	 * Calculate prices using a pricing model.
	 *
	 * @return the map where key=entityId and value=ItemPrice
	 */
	Map<String, ItemPrice> calcPrices();
	
}
