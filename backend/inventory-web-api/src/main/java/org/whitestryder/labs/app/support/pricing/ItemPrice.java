package org.whitestryder.labs.app.support.pricing;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Encapsulate an item price value object.
 */
public class ItemPrice {

	/** The price. */
	private double price;
	
	/**
	 * Instantiates a new item price.
	 */
	public ItemPrice() {
	}

	/**
	 * Instantiates a new item price.
	 *
	 * @param price the price
	 */
	public ItemPrice(double price) {
		super();
		MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
		this.price = new BigDecimal(price).round(mc).doubleValue();
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	
}
