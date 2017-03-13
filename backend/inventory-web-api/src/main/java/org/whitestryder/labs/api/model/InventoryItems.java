package org.whitestryder.labs.api.model;

import java.io.Serializable;
import java.util.List;

import org.whitestryder.labs.core.InventoryItem;

/**
 * Provide a wrapper class that encapsulate a list of InventoryItem
 */
public class InventoryItems implements Serializable {
	
	
	/**
	 * generated
	 */
	private static final long serialVersionUID = 8885063472441586181L;
	
	
	/** The items. */
	private List<InventoryItem> items;
	
	
	/**
	 * Instantiates a new inventory items.
	 */
	public InventoryItems() {
	}

	
	

	/**
	 * Instantiates a new inventory items.
	 *
	 * @param items the items
	 */
	public InventoryItems(List<InventoryItem> items) {
		super();
		this.items = items;
	}




	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public List<InventoryItem> getItems() {
		return items;
	}


	/**
	 * Sets the items.
	 *
	 * @param items the new items
	 */
	public void setItems(List<InventoryItem> items) {
		this.items = items;
	}
	
	
}
