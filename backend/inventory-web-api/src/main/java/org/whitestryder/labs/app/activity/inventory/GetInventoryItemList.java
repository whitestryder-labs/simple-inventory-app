package org.whitestryder.labs.app.activity.inventory;

import java.util.List;

import org.whitestryder.labs.app.activity.inventory.model.InventoryItemDecorator;



/**
 * Provide an interface for getting a list of inventory items.
 */
public interface GetInventoryItemList {


	/**
	 * Execute.
	 *
	 * @return the list
	 */
	List<InventoryItemDecorator> execute();
	
}
