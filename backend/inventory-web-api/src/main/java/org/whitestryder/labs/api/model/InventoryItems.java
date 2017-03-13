package org.whitestryder.labs.api.model;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * Provide a wrapper class that encapsulate a list of InventoryItem
 */
public class InventoryItems extends ResourceSupport {
	
	
	
	/** The items. */
	private List<InventoryItemRepresentation> items;
	
	
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
	public InventoryItems(List<InventoryItemRepresentation> items) {
		super();
		this.items = items;
	}




	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public List<InventoryItemRepresentation> getItems() {
		return items;
	}


	/**
	 * Sets the items.
	 *
	 * @param items the new items
	 */
	public void setItems(List<InventoryItemRepresentation> items) {
		this.items = items;
	}
	
	
}
