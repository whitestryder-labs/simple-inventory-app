package org.whitestryder.labs.app.activity.inventory;

import org.whitestryder.labs.app.support.ApplicationException;
import org.whitestryder.labs.core.InventoryItem;



/**
 * Provide an interface for updating an item
 */
public interface UpdateInventoryItem {

	

	/**
	 * Execute.
	 *
	 * @param item the item
	 * @param refId the ref id
	 * @return the inventory item
	 * @throws ApplicationException the application exception
	 */
	InventoryItem execute(InventoryItem item, String refId) throws ApplicationException;
	
}
