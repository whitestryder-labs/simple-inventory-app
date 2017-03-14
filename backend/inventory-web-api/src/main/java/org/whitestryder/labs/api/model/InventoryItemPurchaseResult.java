package org.whitestryder.labs.api.model;

import org.springframework.hateoas.ResourceSupport;




/**
 * The Class InventoryItemPurchaseResult.
 */
public class InventoryItemPurchaseResult extends ResourceSupport {

	/** The name. */
	private String name;
	
	/** The ref id. */
	private String refId;
	
	/** The purchase price. */
	private double purchasePrice;
	
	
	
	
	
	/**
	 * Instantiates a new inventory item purchase result.
	 */
	public InventoryItemPurchaseResult() {
		super();
	}


	/**
	 * Instantiates a new inventory item purchase result.
	 *
	 * @param name the name
	 * @param refId the ref id
	 * @param purchasePrice the purchase price
	 */
	public InventoryItemPurchaseResult(String name, String refId, double purchasePrice) {
		super();
		this.name = name;
		this.refId = refId;
		this.purchasePrice = purchasePrice;
	}

	
	
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the ref id.
	 *
	 * @return the ref id
	 */
	public String getRefId() {
		return refId;
	}

	/**
	 * Gets the purchase price.
	 *
	 * @return the purchase price
	 */
	public double getPurchasePrice() {
		return purchasePrice;
	}
	
	
	
	
}
