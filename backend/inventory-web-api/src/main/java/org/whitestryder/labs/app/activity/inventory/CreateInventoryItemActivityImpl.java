package org.whitestryder.labs.app.activity.inventory;

import org.whitestryder.labs.app.support.InventoryItemRepository;
import org.whitestryder.labs.core.InventoryItem;

public class CreateInventoryItemActivityImpl implements CreateInventoryItemActivity {

	

	private InventoryItemRepository repository;
	

	public CreateInventoryItemActivityImpl(InventoryItemRepository repository){
		this.repository = repository;
	}
	
	
	@Override
	public void execute(InventoryItem item) {
		repository.save(item);
	}

}
