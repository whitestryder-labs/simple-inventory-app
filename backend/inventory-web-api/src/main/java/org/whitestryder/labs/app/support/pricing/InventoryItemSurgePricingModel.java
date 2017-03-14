package org.whitestryder.labs.app.support.pricing;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.whitestryder.labs.app.support.InventoryItemAccessQuery;
import org.whitestryder.labs.app.support.InventoryItemQuery;
import org.whitestryder.labs.app.support.model.InventoryItemAccessInPastMinutesAgoResult;
import org.whitestryder.labs.app.support.model.InventoryItemBasePriceResult;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Provides implementation of a surge pricing model, similar to Uber, 
 * to increase the price of items by 10% if they have been viewed more than 10 times in an hour.
 */
public class InventoryItemSurgePricingModel implements PricingModel {

	private InventoryItemAccessQuery iiaQuery;
	private InventoryItemQuery iiQuery;
	
	//private int surgePriceModelAccessTimeMins = 60;
	private int surgePriceModelAccessTimeMins = 10;
	private int surgePriceModelViewsPerAccessTimeThreshold = 10;





	private double surgePriceModelPriceIncreasePercent = 0.10; 	//10% 

	public InventoryItemSurgePricingModel() {
	}
	
	public InventoryItemSurgePricingModel(InventoryItemAccessQuery iiaQuery, InventoryItemQuery iiQuery) {
		super();
		this.iiaQuery = iiaQuery;
		this.iiQuery = iiQuery;
	}

	public InventoryItemSurgePricingModel(
			InventoryItemAccessQuery iiaQuery,
			InventoryItemQuery iiQuery,
			int surgePriceModelAccessTimeMins,
			int surgePriceModelViewsPerAccessTimeThreshold,
			double surgePriceModelPriceIncreasePercent) {
		this(iiaQuery, iiQuery);
		this.surgePriceModelAccessTimeMins = surgePriceModelAccessTimeMins;
		this.surgePriceModelViewsPerAccessTimeThreshold = surgePriceModelViewsPerAccessTimeThreshold;
		this.surgePriceModelPriceIncreasePercent = surgePriceModelPriceIncreasePercent;
	}


	@Override
	public Map<String, ItemPrice> calcPrices() {
		// Algorithm:
		// 1. Find count of accesses in past 'x' minutes for each InventoryItem
		// 2. Determine the base prices for each of the inventory items and put in a map 
		// 3. For each result found calculate the surge price and add to the result map
		// 4. Return the result map
		Map<String, ItemPrice> resultMap = Maps.newHashMap();
		
		//Step 1
		Date sinceDate = Date.from(ZonedDateTime.now().minusMinutes(this.surgePriceModelAccessTimeMins).toInstant());
		List<InventoryItemAccessInPastMinutesAgoResult> iiaResults = iiaQuery.findInventoryItemsAccessedSince(sinceDate);
		List<String> iiRefIds = Lists.newArrayList();
		for (InventoryItemAccessInPastMinutesAgoResult result : iiaResults) {
			iiRefIds.add(result.getInventoryItemRefId());
		}
		
		//Step 2
		Map<String, ItemPrice> basePriceMap = Maps.newHashMap();
		List<InventoryItemBasePriceResult> basePrices = Lists.newArrayList();
		if (!iiRefIds.isEmpty()){
			basePrices = iiQuery.findInventoryItemBasePricesForItemRefIds(iiRefIds);
		}
		for (InventoryItemBasePriceResult basePriceResult : basePrices) {
			basePriceMap.put(basePriceResult.getRefId(), new ItemPrice( basePriceResult.getBasePrice() ));
		}
		
		//Step 3
		for (InventoryItemAccessInPastMinutesAgoResult result : iiaResults) {
			if (result.getCount() >= this.surgePriceModelViewsPerAccessTimeThreshold){
				ItemPrice surgePrice = calcPrice( basePriceMap.get(result.getInventoryItemRefId()).getPrice() );
				resultMap.put(result.getInventoryItemRefId(), surgePrice);
			}
		}
		
		//Step 3	
		return resultMap;
	}


	
	public int getSurgePriceModelViewsPerAccessTimeThreshold() {
		return surgePriceModelViewsPerAccessTimeThreshold;
	}
	
	
	
	/*
	 * Helpers
	 */
	
	private ItemPrice calcPrice(double basePrice){
		
		if (surgePriceModelPriceIncreasePercent <= 0){
			return new ItemPrice( basePrice );
		}
		
		return new ItemPrice( basePrice + basePrice * surgePriceModelPriceIncreasePercent );
	}
	
}
