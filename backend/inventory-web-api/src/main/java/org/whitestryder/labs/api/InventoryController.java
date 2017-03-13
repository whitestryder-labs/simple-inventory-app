package org.whitestryder.labs.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.whitestryder.labs.api.model.InventoryItems;
import org.whitestryder.labs.app.activity.inventory.CreateInventoryItemActivity;
import org.whitestryder.labs.app.support.ApplicationException;
import org.whitestryder.labs.app.support.InventoryItemQuery;
import org.whitestryder.labs.core.InventoryItem;

@RestController
public class InventoryController {

	
	private CreateInventoryItemActivity createInventoryItemActivity;
    private InventoryItemQuery inventoryItemQuery;
	
	
	@Autowired
	public InventoryController(
			CreateInventoryItemActivity createInventoryItemActivity,
			InventoryItemQuery inventoryItemQuery){
		this.createInventoryItemActivity = createInventoryItemActivity;
		this.inventoryItemQuery = inventoryItemQuery;
	}
	
	
    @RequestMapping(path = "/api/inventory-item/{id}/purchase", method = RequestMethod.POST)
    public String buyItemFromInventory(@PathVariable String id) {
        return String.format("You requested to purchase item with id=%s", id);
    }
    
    @RequestMapping(path = "/api/inventory-item", method = RequestMethod.GET)
    public ResponseEntity<InventoryItems> getInventoryItems() {
    	InventoryItems inventoryItems = 
    			new InventoryItems(inventoryItemQuery.findAll());
    	
    	return ResponseEntity
    				.status(HttpStatus.OK)
    				.body(inventoryItems);
    }
    
    @RequestMapping(path = "/api/inventory-item/{refId}", method = RequestMethod.GET)
    public String getInventoryItem(@PathVariable String refId) {
        return "Inventory Item " + refId;
    }
    
    
    
    @RequestMapping(path = "/api/inventory-item", method = RequestMethod.POST)
    public ResponseEntity<?> createInventoryItem(
    		@RequestBody InventoryItem inventoryItem) throws ApplicationException {
    	
    	InventoryItem itemCreated = createInventoryItemActivity.execute(inventoryItem);
    	
        return ResponseEntity
        		.status(HttpStatus.CREATED)
				.header("Location", "http://localhost:8181/api/inventory-item/" + itemCreated.getExternalReferenceId())
        		.body("Created");
    }
}
