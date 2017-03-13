package org.whitestryder.labs.api.model;

import org.springframework.hateoas.ResourceSupport;



public class InventoryItemRepresentation extends ResourceSupport {


	/**  The external reference id meant for use outside the system */
	private String externalReferenceId;

	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The price. */
	private int price;
	
	/** The quantity in stock. */
	private int quantityInStock;
	
	
	public InventoryItemRepresentation() {
	}


	public InventoryItemRepresentation(String externalReferenceId, String name, String description, int price,
			int quantityInStock) {
		super();
		this.externalReferenceId = externalReferenceId;
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantityInStock = quantityInStock;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getPrice() {
		return price;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getQuantityInStock() {
		return quantityInStock;
	}


	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}


	public String getExternalReferenceId() {
		return externalReferenceId;
	}
	
	
	
	
	
}