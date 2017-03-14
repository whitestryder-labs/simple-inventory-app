package org.whitestryder.labs.app.activity.inventory;

import org.whitestryder.labs.app.activity.inventory.model.InventoryItemDecorator;
import org.whitestryder.labs.app.support.ApplicationException;


/**
 * Provides an interface for buying an inventory item.
 */
public interface BuyInventoryItem {

	/**
	 * Execute.
	 *
	 * @param refId the ref id
	 * @return the decorated inventory item
	 * @throws ApplicationException the application exception
	 */
	InventoryItemDecorator execute(String refId) throws ApplicationException;
	
}
