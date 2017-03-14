package org.whitestryder.labs.app.activity.inventory;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.whitestryder.labs.app.activity.inventory.model.InventoryItemDecorator;
import org.whitestryder.labs.app.support.InventoryItemQuery;
import org.whitestryder.labs.app.support.pricing.ItemPrice;
import org.whitestryder.labs.app.support.pricing.PricingModel;
import org.whitestryder.labs.core.InventoryItem;

import com.google.common.collect.Lists;



/**
 * Provides a way to get a list of inventory items and applying a pricing model to each item's base price.
 */
public class GetInventoryItemListImpl implements GetInventoryItemList {

	/** The log. */
	private static Logger LOG = LoggerFactory.getLogger(GetInventoryItemListImpl.class);
	

	
	/** The inventory item query service . */
	private InventoryItemQuery query;
	
	/** The pricing model. */
	private PricingModel pricingModel;



	/**
	 * Instantiates a new gets the inventory item list impl.
	 *
	 * @param query the query
	 * @param pricingModel the pricing model
	 */
	public GetInventoryItemListImpl(
			InventoryItemQuery query,
			PricingModel pricingModel){
		this.query = query;
		this.pricingModel = pricingModel;
	}
	
	

	@Override
	//@Transactional( readOnly = true )
	public List<InventoryItemDecorator> execute() {
		
		List<InventoryItemDecorator> decoratedItems = Lists.newArrayList();
		List<InventoryItem> inventoryItems = query.findAll();
    	
		//Apply pricing model
    	if (!inventoryItems.isEmpty()){
    		
    		if (pricingModel != null){
    			
    			Map<String, ItemPrice> priceModelMap = pricingModel.calcPrices();

				for (InventoryItem inventoryItem : inventoryItems) {
					if (priceModelMap.containsKey(inventoryItem.getExternalReferenceId())){
						ItemPrice currentItemPrice = priceModelMap.get(inventoryItem.getExternalReferenceId());
						decoratedItems.add(new InventoryItemDecorator(inventoryItem, currentItemPrice.getPrice()));
						LOG.info(String.format("Applied price model to InventoryItem with refId '%s', basePrice=%d, currentPrice=%.2f", 
								inventoryItem.getExternalReferenceId(), inventoryItem.getPrice(), currentItemPrice.getPrice()));
					} else {
						decoratedItems.add(new InventoryItemDecorator(inventoryItem));
					}
				}
    		} else {
    			for (InventoryItem inventoryItem : inventoryItems) {
					decoratedItems.add(new InventoryItemDecorator(inventoryItem));
				}
    		}
    	}
		
		return decoratedItems;
	}

}

