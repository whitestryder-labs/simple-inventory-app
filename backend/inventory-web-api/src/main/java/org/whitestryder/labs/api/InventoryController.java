package org.whitestryder.labs.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.whitestryder.labs.api.model.InventoryItemRepresentation;
import org.whitestryder.labs.api.model.InventoryItems;
import org.whitestryder.labs.app.activity.inventory.CreateInventoryItem;
import org.whitestryder.labs.app.activity.inventory.GetSingleInventoryItem;
import org.whitestryder.labs.app.support.ApplicationException;
import org.whitestryder.labs.app.support.InventoryItemQuery;
import org.whitestryder.labs.core.InventoryItem;

import com.google.common.collect.Lists;



/**
 * Provides a controller for the operations on an InventoryItem resource.
 */
@RestController
public class InventoryController {

	
	/** The create inventory item activity. */
	private CreateInventoryItem createInventoryItem;
	
	private GetSingleInventoryItem getSingleInventoryItem;
    
    /** The inventory item query. */
    private InventoryItemQuery inventoryItemQuery;

	
	
	/**
	 * Instantiates a new inventory controller.
	 *
	 * @param createInventoryItemActivity the create inventory item activity
	 * @param inventoryItemQuery the inventory item query
	 * @param getSingleInventoryItem the get single inventory item
	 */
	@Autowired
	public InventoryController(
			CreateInventoryItem createInventoryItemActivity,
			InventoryItemQuery inventoryItemQuery,
			GetSingleInventoryItem getSingleInventoryItem){
		this.createInventoryItem = createInventoryItemActivity;
		this.inventoryItemQuery = inventoryItemQuery;
		this.getSingleInventoryItem = getSingleInventoryItem;
	}
	
	
    /**
     * Buy item from inventory.
     *
     * @param id the id
     * @return the string
     */
    @RequestMapping(path = "/api/inventory-item/{id}/purchase", method = RequestMethod.POST)
    public String buyItemFromInventory(@PathVariable String id) {
        return String.format("You requested to purchase item with id=%s", id);
    }
    
    /**
     * Gets the inventory items.
     *
     * @return the inventory items
     * @throws ApplicationException the application exception
     */
    @RequestMapping(path = "/api/inventory-item", method = RequestMethod.GET)
    public ResponseEntity<InventoryItems> getInventoryItems() throws ApplicationException {
    	
    	List<InventoryItem> items = inventoryItemQuery.findAll();
    	
    	List<InventoryItemRepresentation> itemsRepList = Lists.newArrayList();
    	for (InventoryItem item : items) {
    		
    		InventoryItemRepresentation rep = 
    				new InventoryItemRepresentation(
    	    				item.getExternalReferenceId(),
    	    				item.getName(),
    	    				item.getDescription(),
    	    				item.getPrice(),
    	    				item.getQuantityInStock());
    		
    		
    		itemsRepList.add(rep);
		}
    	
    	InventoryItems inventoryItems = 
    			new InventoryItems(itemsRepList);
    	
    	return ResponseEntity
    				.status(HttpStatus.OK)
    				.body(inventoryItems);
    }
    
    
    
    /**
     * Gets the inventory item.
     *
     * @param refId the ref id
     * @return the inventory item
     * @throws ApplicationException the application exception
     */
    @RequestMapping(path = "/api/inventory-item/{refId}", method = RequestMethod.GET)
    public ResponseEntity<InventoryItemRepresentation> getInventoryItem(@PathVariable String refId) throws ApplicationException {
        
    	InventoryItem item = getSingleInventoryItem.execute(refId);
    	
    	InventoryItemRepresentation rep = 
				new InventoryItemRepresentation(
	    				item.getExternalReferenceId(),
	    				item.getName(),
	    				item.getDescription(),
	    				item.getPrice(),
	    				item.getQuantityInStock());
    	
    	return ResponseEntity
				.status(HttpStatus.OK)
				.body(rep);
    }
    
    
    
    /**
     * Creates the inventory item.
     *
     * @param inventoryItem the inventory item
     * @return the response entity
     * @throws ApplicationException the application exception
     */
    @RequestMapping(path = "/api/inventory-item", method = RequestMethod.POST)
    public ResponseEntity<?> createInventoryItem(
    		@RequestBody InventoryItem inventoryItem) throws ApplicationException {
    	
    	InventoryItem itemCreated = createInventoryItem.execute(inventoryItem);
    	
    	String locationUri = 
    			linkTo(methodOn(InventoryController.class).getInventoryItem(itemCreated.getExternalReferenceId())).toUri().toString();
    	
        return ResponseEntity
        		.status(HttpStatus.CREATED)
				.header("Location", locationUri)
        		.body("Created");
    }
    

}
