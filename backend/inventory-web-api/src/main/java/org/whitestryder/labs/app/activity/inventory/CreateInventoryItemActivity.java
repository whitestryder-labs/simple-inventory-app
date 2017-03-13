package org.whitestryder.labs.app.activity.inventory;

import org.whitestryder.labs.app.support.ApplicationException;
import org.whitestryder.labs.core.InventoryItem;

public interface CreateInventoryItemActivity {

	
	InventoryItem execute(InventoryItem item) throws ApplicationException;
	
}
