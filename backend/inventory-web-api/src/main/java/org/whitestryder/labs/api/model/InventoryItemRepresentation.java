package org.whitestryder.labs.api.model;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;
import org.whitestryder.labs.api.InventoryController;
import org.whitestryder.labs.app.support.ApplicationException;




/**
 * The Class InventoryItemRepresentation.
 */
public class InventoryItemRepresentation extends ResourceSupport {


	/**   The external reference id meant for use outside the system. */
	private String externalReferenceId;

	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The price. */
	private double price;
	
	/** The quantity in stock. */
	private int quantityInStock;
	
	
	/**
	 * Instantiates a new inventory item representation.
	 */
	public InventoryItemRepresentation() {
	}


	/**
	 * Instantiates a new inventory item representation.
	 *
	 * @param externalReferenceId the external reference id
	 * @param name the name
	 * @param description the description
	 * @param price the price
	 * @param quantityInStock the quantity in stock
	 * @throws ApplicationException the application exception
	 */
	public InventoryItemRepresentation(String externalReferenceId, String name, String description, double price,
			int quantityInStock) throws ApplicationException {
		super();
		this.externalReferenceId = externalReferenceId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantityInStock = quantityInStock;
		
		this.add(linkTo(methodOn(InventoryController.class).getInventoryItem(externalReferenceId)).withSelfRel());
		this.add(linkTo(methodOn(InventoryController.class).buyItemFromInventory(externalReferenceId))
				.withRel(LinkRelType.RELATED.combineWith(LinkRelType.X_BUY)));
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
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}


	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(int price) {
		this.price = price;
	}


	/**
	 * Gets the quantity in stock.
	 *
	 * @return the quantity in stock
	 */
	public int getQuantityInStock() {
		return quantityInStock;
	}


	/**
	 * Sets the quantity in stock.
	 *
	 * @param quantityInStock the new quantity in stock
	 */
	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}


	/**
	 * Gets the external reference id.
	 *
	 * @return the external reference id
	 */
	public String getExternalReferenceId() {
		return externalReferenceId;
	}
	
	
	
	
	
}
