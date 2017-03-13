package org.whitestryder.labs.core;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;




/**
 * Keep a record of when an inventory item is viewed/accessed.
 * This entity is intentionally not tied too tightly to the InventoryItem,
 * that is, there is no referential integrity enforced for these access record
 * entries because it serves as an access event log.  In other words, we want
 * to keep the event log entries around even if the original item is deleted.
 * 
 * @author steve
 *
 */
@Entity
public class InventoryItemAccess {

	/** The Max desc length. */
	public static int MaxInventoryItemIdLength = 64;
	
	/** The Max accessed by length. */
	public static int MaxAccessedByLength = 64;
	
	
	/** The id (internal use only). */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@JsonIgnore
	private long id;
	
	
	
	/**  The external reference id of the inventory item that was accessed. */
	@Column(nullable=false, updatable=false, unique=false)
	private String inventoryItemRefId;
	
	
	/**  The external reference id of the inventory item that was accessed. */
	@Column(nullable=false, updatable=false, unique=false)
	private Date dateAccessed;
	
	/**  The external reference id of the inventory item that was accessed. */
	@Column(nullable=false, updatable=false, unique=false)
	private String accessedBy;

	
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
	protected InventoryItemAccess() {
	}
	
	

	protected InventoryItemAccess(String inventoryItemRefId, String accessedBy) {
		super();
		this.setInventoryItemRefId(inventoryItemRefId);
		this.setAccessedBy(accessedBy);
		this.setDateAccessed(Date.from(ZonedDateTime.now().toInstant()));
	}






	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	protected void setId(long id) {
		this.id = id;
	}
	
	

	

	

	

	public String getInventoryItemRefId() {
		return inventoryItemRefId;
	}



	protected void setInventoryItemRefId(String inventoryItemRefId) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(inventoryItemRefId), "InventoryItemAccess 'inventoryItemRefId' must have a value");
		Preconditions.checkArgument(inventoryItemRefId.length() <= MaxInventoryItemIdLength,
				String.format("InventoryItemAccess 'inventoryItemRefId' must be <= %s characters in length", MaxInventoryItemIdLength));
		this.inventoryItemRefId = inventoryItemRefId;
	}


	


	public Date getDateAccessed() {
		return dateAccessed;
	}


	protected void setDateAccessed(Date dateAccessed) {
		Preconditions.checkNotNull(dateAccessed, "InventoryItemAccess 'dateAccessed' must have a value");
		this.dateAccessed = dateAccessed;
	}


	public String getAccessedBy() {
		return accessedBy;
	}


	protected void setAccessedBy(String accessedBy) {
		Preconditions.checkArgument(!Strings.isNullOrEmpty(accessedBy), "InventoryItemAccess 'accessedBy' must have a value");
		Preconditions.checkArgument(accessedBy.length() <= MaxAccessedByLength,
				String.format("InventoryItemAccess 'accessedBy' must be <= %s characters in length", MaxAccessedByLength));
		this.accessedBy = accessedBy;
	}
	
	
	
	
	
	
	
}

