package org.whitestryder.labs.app.activity.inventory;

import org.whitestryder.labs.app.activity.inventory.model.InventoryItemDecorator;
import org.whitestryder.labs.app.support.ApplicationException;

/**
 * Provide an interface for getting a single InventoryItem.
 */
public interface GetSingleInventoryItem {

	/**
	 * Execute.
	 *
	 * @param refId the ref id
	 * @return the inventory item
	 */
	InventoryItemDecorator execute(String refId) throws ApplicationException;
	
}
