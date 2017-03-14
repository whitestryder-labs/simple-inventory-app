package org.whitestryder.labs.app.activity.inventory.model;

import org.whitestryder.labs.core.InventoryItem;



/**
 * Provide a decorator for InventoryItem that uses an alternate price.
 */
public class InventoryItemDecorator {

	/** The item. */
	private InventoryItem item;

	/** The price. */
	private double price;
	
	
	

	/**
	 * Instantiates a new inventory item decorator.
	 *
	 * @param item the item
	 */
	public InventoryItemDecorator(InventoryItem item) {
		super();
		this.item = item;
		this.price = item.getPrice();
	}
	

	/**
	 * Instantiates a new inventory item decorator.
	 *
	 * @param item the item
	 * @param price the price
	 */
	public InventoryItemDecorator(InventoryItem item, double price) {
		super();
		this.item = item;
		this.price = price;
	}


	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public double getCurrentPrice() {
		return price;
	}


	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return item.getName();
	}
	
	
	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return item.getDescription();
	}


	/**
	 * Gets the external reference id.
	 *
	 * @return the external reference id
	 */
	public String getExternalReferenceId() {
		return item.getExternalReferenceId();
	}


	/**
	 * Gets the quantity in stock.
	 *
	 * @return the quantity in stock
	 */
	public int getQuantityInStock() {
		return item.getQuantityInStock();
	}
	
}
