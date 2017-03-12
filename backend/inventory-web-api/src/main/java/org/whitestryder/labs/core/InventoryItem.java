package org.whitestryder.labs.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;


/**
 * Encapsulates an item in a shop inventory.
 * @author steve
 *
 */
@Entity
public class InventoryItem {

	public static int MaxNameLength = 64;
	public static int MaxDescLength = 256;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	private String description;
	private int price;
	private int quantityInStock;
	protected long getId() {
		return id;
	}
	
	
	/**
	 * Constructor required for ORM
	 */
	protected InventoryItem() {
	}
	
	
	public InventoryItem(String name, String description, int price, int quantityInStock) {
		super();
		this.setName(name);
		this.setDescription(description);
		this.setPrice(price);
		this.setQuantityInStock(quantityInStock);
	}





	protected void setId(long id) {
		this.id = id;
	}
	
	
	protected String getName() {
		return name;
	}
	
	
	protected void setName(String name) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "InventoryItem name must not be null or empty");
		Preconditions.checkArgument(name.length() <= MaxNameLength, 
				String.format("InventoryItem name must be <= %d characters in length", MaxNameLength));
		this.name = name;
	}
	
	
	protected String getDescription() {
		return description;
	}
	
	
	protected void setDescription(String description) {
		Preconditions.checkArgument(name.length() <= MaxDescLength,
				String.format("InventoryItem name must be <= %s characters in length", MaxDescLength));
		this.description = description;
	}
	
	
	protected int getPrice() {
		return price;
	}
	
	
	protected void setPrice(int price) {
		Preconditions.checkArgument(price >= 0, "InventoryItem price must be >= 0");
		this.price = price;
	}
	
	
	protected int getQuantityInStock() {
		return quantityInStock;
	}
	
	
	protected void setQuantityInStock(int quantityInStock) {
		Preconditions.checkArgument(quantityInStock >= 0, "InventoryItem quantityInStock must be >= 0");
		this.quantityInStock = quantityInStock;
	}
	
	
	
	
	
}


