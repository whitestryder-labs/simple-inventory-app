package org.whitestryder.labs.api.model;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.ResourceSupport;
import org.whitestryder.labs.api.InventoryController;
import org.whitestryder.labs.app.support.ApplicationException;

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
	 * @throws ApplicationException 
	 */
	public InventoryItems(List<InventoryItemRepresentation> items) throws ApplicationException {
		super();
		this.items = items;
		
		this.add(linkTo(methodOn(InventoryController.class).getInventoryItems()).withSelfRel());
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
