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
import org.whitestryder.labs.api.model.InventoryItemPurchaseResult;
import org.whitestryder.labs.api.model.InventoryItemRepresentation;
import org.whitestryder.labs.api.model.InventoryItems;
import org.whitestryder.labs.app.activity.inventory.BuyInventoryItem;
import org.whitestryder.labs.app.activity.inventory.CreateInventoryItem;
import org.whitestryder.labs.app.activity.inventory.GetInventoryItemList;
import org.whitestryder.labs.app.activity.inventory.GetSingleInventoryItem;
import org.whitestryder.labs.app.activity.inventory.UpdateInventoryItem;
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
	
	/** The buy inventory item. */
	private BuyInventoryItem buyInventoryItem;
	
	/** The update inventory item. */
	private UpdateInventoryItem updateInventoryItem;
	
	
	/**
	 * Instantiates a new inventory controller.
	 *
	 * @param createInventoryItemActivity the create inventory item activity
	 * @param getSingleInventoryItem the get single inventory item
	 * @param getInventoryItemList the get inventory item list
	 * @param buyInventoryItem the buy inventory item
	 * @param updateInventoryItem the update inventory item
	 */
	@Autowired
	public InventoryController(
			CreateInventoryItem createInventoryItemActivity,
			GetSingleInventoryItem getSingleInventoryItem,
			GetInventoryItemList getInventoryItemList,
			BuyInventoryItem buyInventoryItem,
			UpdateInventoryItem updateInventoryItem){
		this.createInventoryItem = createInventoryItemActivity;
		this.getSingleInventoryItem = getSingleInventoryItem;
		this.getInventoryItemList = getInventoryItemList;
		this.buyInventoryItem = buyInventoryItem;
		this.updateInventoryItem = updateInventoryItem;
	}
	
	
    /**
     * Buy item from inventory.
     *
     * @param id the refId
     * @return the string
     * @throws ApplicationException 
     */
    @RequestMapping(path = "/api/inventory-item/{refId}/purchase", method = RequestMethod.POST)
    public ResponseEntity<InventoryItemPurchaseResult> buyItemFromInventory(@PathVariable String refId) throws ApplicationException {
        
    	InventoryItemDecorator itemBought = buyInventoryItem.execute(refId);
    	
    	InventoryItemPurchaseResult purchaseResult = 
    			new InventoryItemPurchaseResult(
    					itemBought.getName(),
    					itemBought.getExternalReferenceId(),
    					itemBought.getCurrentPrice());
    	
    	return ResponseEntity.ok().body(purchaseResult);
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
    
    
    
    @RequestMapping(path = "/api/inventory-item/{refId}", method = RequestMethod.PUT)
    public ResponseEntity<InventoryItemRepresentation> updateInventoryItem(
    		@RequestBody InventoryItem inventoryItem, @PathVariable String refId) throws ApplicationException {
        
    	InventoryItem item = updateInventoryItem.execute(inventoryItem, refId);
    	
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

}
