package org.whitestryder.labs.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.whitestryder.labs.app.activity.inventory.CreateInventoryItemActivity;
import org.whitestryder.labs.core.InventoryItem;

@RestController
public class InventoryController {

	
	private CreateInventoryItemActivity createInventoryItemActivity;
    
	
	
	@Autowired
	public InventoryController(CreateInventoryItemActivity createInventoryItemActivity){
		this.createInventoryItemActivity = createInventoryItemActivity;
	}
	
	
    @RequestMapping(path = "/api/inventory-item/{id}/purchase", method = RequestMethod.POST)
    public String buyItemFromInventory(@PathVariable String id) {
        return String.format("You requested to purchase item with id=%s", id);
    }
    
    @RequestMapping(path = "/api/inventory-item", method = RequestMethod.GET)
    public String getInventory() {
        return "Inventory TBD";
    }
    
    @RequestMapping(path = "/api/inventory-item/{id}", method = RequestMethod.GET)
    public String getInventoryItem(@PathVariable String id) {
        return "Inventory Item " + id;
    }
    
    
    
    @RequestMapping(path = "/api/inventory-item", method = RequestMethod.POST)
    public ResponseEntity<?> createInventoryItem(
    		@RequestBody InventoryItem inventoryItem) {
    	
    	createInventoryItemActivity.execute(inventoryItem);
    	
        return ResponseEntity.status(HttpStatus.CREATED).body("Created");
    }
}
