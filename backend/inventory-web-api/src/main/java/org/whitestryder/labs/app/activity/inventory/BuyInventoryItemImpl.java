package org.whitestryder.labs.app.activity.inventory;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.whitestryder.labs.app.activity.inventory.model.InventoryItemDecorator;
import org.whitestryder.labs.app.support.ApplicationConflictException;
import org.whitestryder.labs.app.support.ApplicationException;
import org.whitestryder.labs.app.support.AuthenticatedUserContextService;
import org.whitestryder.labs.app.support.EntityNotFoundException;
import org.whitestryder.labs.app.support.InventoryItemQuery;
import org.whitestryder.labs.app.support.InventoryItemRepository;
import org.whitestryder.labs.app.support.UserContext;
import org.whitestryder.labs.app.support.pricing.ItemPrice;
import org.whitestryder.labs.app.support.pricing.PricingModel;
import org.whitestryder.labs.core.InventoryItem;
import org.whitestryder.labs.core.support.InventoryItemOutOfStockException;



public class BuyInventoryItemImpl implements BuyInventoryItem {

	/** The log. */
	private static Logger LOG = LoggerFactory.getLogger(BuyInventoryItemImpl.class);

	/** The repository. */
	private InventoryItemRepository repository;
	
	/** The query. */
	private InventoryItemQuery query;
	
	/** The user context service. */
	private AuthenticatedUserContextService userContextService;
	
	/** The pricing model. */
	private PricingModel pricingModel;


	/**
	 * Instantiates a new buy inventory item impl.
	 *
	 * @param repository the repository
	 * @param query the query
	 * @param userContextService the user context service
	 * @param pricingModel the pricing model
	 */
	public BuyInventoryItemImpl(InventoryItemRepository repository,
			InventoryItemQuery query,
			AuthenticatedUserContextService userContextService,
			PricingModel pricingModel){
		this.repository = repository;
		this.userContextService = userContextService;
		this.query = query;
		this.pricingModel = pricingModel;
	}

	
	
	@Override
	public InventoryItemDecorator execute(String refId) throws ApplicationException {
		UserContext user = userContextService.getAuthenticatedUser();
		
		List<InventoryItem> foundItems = query.findByExternalReferenceId(refId);
		
		if (foundItems.isEmpty()){
			throw new EntityNotFoundException(
					String.format("The inventory item with refId '%s' does not exist", refId));
		} else if (foundItems.size() > 1){
    		throw new ApplicationException(
    				String.format("More than one item with the refId %s was found!", refId));
    	}
    	
    	InventoryItem itemToBuy = foundItems.get(0);
    	
    	//Use the pricing model to determine the current price, we will
    	//send it back to the user who bought the item as a confirmation.
    	InventoryItemDecorator decoratedItemBought = new InventoryItemDecorator(itemToBuy);
    	ItemPrice currentItemPrice = new ItemPrice(itemToBuy.getPrice());
    	if (pricingModel != null){
			Map<String, ItemPrice> priceModelMap = pricingModel.calcPrices();
			if (priceModelMap.containsKey(itemToBuy.getExternalReferenceId())){
				currentItemPrice = priceModelMap.get(itemToBuy.getExternalReferenceId());
				decoratedItemBought = new InventoryItemDecorator(itemToBuy, currentItemPrice.getPrice());
			} else {
				decoratedItemBought = new InventoryItemDecorator(itemToBuy);
			}
		}

    	try {
    		//Attempt to buy this item
    		itemToBuy.buy();
    	} catch (InventoryItemOutOfStockException ex){
    		LOG.warn(
    			String.format("User '%s' tried to buy item %s for $%.2f but the item is out of stock",
    					user.getUsername(), itemToBuy.toString(), currentItemPrice.getPrice()));
    		throw new ApplicationConflictException(
    				String.format("Item %s with refId '%s' is currently out of stock", itemToBuy.getName(), itemToBuy.getExternalReferenceId()));
    	}
    	repository.save(itemToBuy);
    	
		LOG.info(String.format("User '%s' bought item %s for $%.2f", user.getUsername(), itemToBuy.toString(), currentItemPrice.getPrice()));
		
		return decoratedItemBought;
	}

}
