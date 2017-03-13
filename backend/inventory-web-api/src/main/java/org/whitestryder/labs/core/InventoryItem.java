package org.whitestryder.labs.core;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;



/**
 * Encapsulates an item in a shop inventory.
 * @author steve
 *
 */
@Entity
public class InventoryItem {

	/** The Max name length. */
	public static int MaxNameLength = 64;
	
	/** The Max desc length. */
	public static int MaxDescLength = 256;
	
	/** The Max ext ref id length. */
	public static int MaxExtRefIdLength = 64;
	
	
	/** The id (internal use only). */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@JsonIgnore
	private long id;
	
	/**  The external reference id meant for use outside the system, the 'id' field is internal. */
	@Column(nullable=false, updatable=false, unique=true)
	private String externalReferenceId;

	/** The name. */
	@Column(nullable=false, unique=true)
	private String name;
	
	/** The description. */
	@Column(nullable=true)
	private String description;
	
	/** The price. */
	@Column(nullable=false)
	private int price;
	
	/** The quantity in stock. */
	@Column(nullable=false)
	private int quantityInStock;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	protected long getId() {
		return id;
	}
	
	
	/**
	 * Constructor required for ORM.
	 */
	protected InventoryItem() {
	}
	
	
	/**
	 * Instantiates a new inventory item.
	 *
	 * @param name the name
	 * @param description the description
	 * @param price the price
	 * @param quantityInStock the quantity in stock
	 */
	public InventoryItem(String name, String description, int price, int quantityInStock) {
		super();
		this.setName(name);
		this.setDescription(description);
		this.setPrice(price);
		this.setQuantityInStock(quantityInStock);
		this.setExternalReferenceId(UUID.randomUUID().toString());
	}





	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	protected void setId(long id) {
		this.id = id;
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
	protected void setName(String name) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "InventoryItem 'name' must have a value");
		Preconditions.checkArgument(name.length() <= MaxNameLength, 
				String.format("InventoryItem 'name' must be <= %d characters in length", MaxNameLength));
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
	protected void setDescription(String description) {
		Preconditions.checkArgument(name.length() <= MaxDescLength,
				String.format("InventoryItem 'name' must be <= %s characters in length", MaxDescLength));
		this.description = description;
	}
	
	
	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public int getPrice() {
		return price;
	}
	
	
	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	protected void setPrice(int price) {
		Preconditions.checkArgument(price >= 0, "InventoryItem 'price' must be >= 0");
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
	protected void setQuantityInStock(int quantityInStock) {
		Preconditions.checkArgument(quantityInStock >= 0, "InventoryItem 'quantityInStock' must be >= 0");
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


	/**
	 * Sets the external reference id.
	 *
	 * @param externalReferenceId the new external reference id
	 */
	protected void setExternalReferenceId(String externalReferenceId) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(externalReferenceId), "InventoryItem 'externalReferenceId' must have a value");
		Preconditions.checkArgument(externalReferenceId.length() <= MaxExtRefIdLength,
				String.format("InventoryItem 'externalReferenceId' must be <= %s characters in length", MaxExtRefIdLength));
		this.externalReferenceId = externalReferenceId;
	}


	/**
	 * Buy this item.
	 */
	public void buy() {
		Preconditions.checkState(this.quantityInStock > 0, "This item is out of stock");
		this.quantityInStock--;
	}


	@Override
	public String toString() {
		return "InventoryItem [getId()=" + getId() + ", getName()=" + getName() + ", getDescription()="
				+ getDescription() + ", getPrice()=" + getPrice() + ", getQuantityInStock()=" + getQuantityInStock()
				+ ", getExternalReferenceId()=" + getExternalReferenceId() + "]";
	}


	/**
	 * Creates the access record.
	 *
	 * @param accessedBy the accessed by
	 * @return the inventory item access
	 */
	public InventoryItemAccess createAccessRecord(String accessedBy) {
		InventoryItemAccess itemAccess = 
				new InventoryItemAccess(
						this.externalReferenceId,
						accessedBy);
		return itemAccess;
	}


	/**
	 * Creates the access record with accessedBy 'anonymous'.
	 *
	 * @return the inventory item access
	 */
	public InventoryItemAccess createAccessRecord() {
		return createAccessRecord("anonymous");
	}
	
	
	
	
	
}


