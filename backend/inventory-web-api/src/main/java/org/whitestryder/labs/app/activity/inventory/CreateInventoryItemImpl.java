package org.whitestryder.labs.app.activity.inventory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.whitestryder.labs.app.support.ApplicationConflictException;
import org.whitestryder.labs.app.support.ApplicationException;
import org.whitestryder.labs.app.support.AuthenticatedUserContextService;
import org.whitestryder.labs.app.support.InventoryItemQuery;
import org.whitestryder.labs.app.support.InventoryItemRepository;
import org.whitestryder.labs.app.support.UserContext;
import org.whitestryder.labs.core.InventoryItem;



/**
 * Provides a way to create an inventory item for authenticated users.
 */
public class CreateInventoryItemImpl implements CreateInventoryItem {

	/** The log. */
	private static Logger LOG = LoggerFactory.getLogger(CreateInventoryItemImpl.class);

	/** The repository. */
	private InventoryItemRepository repository;
	
	/** The query. */
	private InventoryItemQuery query;
	
	/** The user context service. */
	private AuthenticatedUserContextService userContextService;
	

	/**
	 * Instantiates a new creates the inventory item activity impl.
	 *
	 * @param repository the repository
	 * @param query the query
	 * @param userContextService the user context service
	 */
	public CreateInventoryItemImpl(InventoryItemRepository repository,
			InventoryItemQuery query,
			AuthenticatedUserContextService userContextService){
		this.repository = repository;
		this.userContextService = userContextService;
		this.query = query;
	}
	
	
	/* (non-Javadoc)
	 * @see org.whitestryder.labs.app.activity.inventory.CreateInventoryItemActivity#execute(org.whitestryder.labs.core.InventoryItem)
	 */
	@Override
	public InventoryItem execute(InventoryItem item) throws ApplicationException {
		
		UserContext user = userContextService.getAuthenticatedUser();
		
		List<InventoryItem> foundItems = query.findByName(item.getName());
		
		if (!foundItems.isEmpty()){
			throw new ApplicationConflictException(
					String.format("There is already an item with name '%s', please choose a different name", item.getName()));
		}
		
		//Need to ensure item gets created properly
		InventoryItem itemToCreate = 
				new InventoryItem(item.getName(), item.getDescription(), item.getPrice(), item.getQuantityInStock());
		
		repository.save(itemToCreate);
		
		LOG.info(String.format("User '%s' added item %s", user.getUsername(), item.toString()));
		
		return itemToCreate;
	}

}
