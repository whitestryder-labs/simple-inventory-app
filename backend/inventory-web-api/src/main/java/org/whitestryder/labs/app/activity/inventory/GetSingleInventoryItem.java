package org.whitestryder.labs.app.activity.inventory;

import org.whitestryder.labs.app.support.ApplicationException;
import org.whitestryder.labs.core.InventoryItem;

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
	InventoryItem execute(String refId) throws ApplicationException;
	
}
