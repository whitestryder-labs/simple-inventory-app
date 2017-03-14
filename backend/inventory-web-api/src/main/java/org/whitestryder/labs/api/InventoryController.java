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
import org.whitestryder.labs.app.activity.inventory.GetInventoryItemList;
import org.whitestryder.labs.app.activity.inventory.GetSingleInventoryItem;
import org.whitestryder.labs.app.activity.inventory.model.InventoryItemDecorator;
import org.whitestryder.labs.app.support.ApplicationException;
import org.whitestryder.labs.core.InventoryItem;

import com.google.common.collect.Lists;



/**
 * Provides a controller for the operations on an InventoryItem resource.
 */
@RestController
public class InventoryController {

	
	/** The create inventory item activity. */
	private CreateInventoryItem createInventoryItem;
	
	/** The get single inventory item. */
	private GetSingleInventoryItem getSingleInventoryItem;

	/** The get inventory item list. */
	private GetInventoryItemList getInventoryItemList;
	

	
	
	/**
	 * Instantiates a new inventory controller.
	 *
	 * @param createInventoryItemActivity the create inventory item activity
	 * @param getSingleInventoryItem the get single inventory item
	 * @param getInventoryItemList the get inventory item list
	 */
	@Autowired
	public InventoryController(
			CreateInventoryItem createInventoryItemActivity,
			GetSingleInventoryItem getSingleInventoryItem,
			GetInventoryItemList getInventoryItemList){
		this.createInventoryItem = createInventoryItemActivity;
		this.getSingleInventoryItem = getSingleInventoryItem;
		this.getInventoryItemList = getInventoryItemList;
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
    	
    	List<InventoryItemDecorator> items = getInventoryItemList.execute();
    	
    	List<InventoryItemRepresentation> itemsRepList = Lists.newArrayList();
    	for (InventoryItemDecorator item : items) {
    		
    		InventoryItemRepresentation rep = 
    				new InventoryItemRepresentation(
    	    				item.getExternalReferenceId(),
    	    				item.getName(),
    	    				item.getDescription(),
    	    				item.getCurrentPrice(),
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
        
    	InventoryItemDecorator item = getSingleInventoryItem.execute(refId);
    	
    	InventoryItemRepresentation rep = 
				new InventoryItemRepresentation(
	    				item.getExternalReferenceId(),
	    				item.getName(),
	    				item.getDescription(),
	    				item.getCurrentPrice(),
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
