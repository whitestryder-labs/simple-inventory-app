package org.whitestryder.labs.app.activity.inventory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.whitestryder.labs.app.support.ApplicationException;
import org.whitestryder.labs.app.support.AuthenticatedUserContextService;
import org.whitestryder.labs.app.support.EntityNotFoundException;
import org.whitestryder.labs.app.support.InventoryItemQuery;
import org.whitestryder.labs.app.support.InventoryItemRepository;
import org.whitestryder.labs.app.support.UserContext;
import org.whitestryder.labs.core.InventoryItem;



/**
 * Provides a way to update an inventory item for an authenticated 'admin' role user.
 */
public class UpdateInventoryItemImpl implements UpdateInventoryItem {

	/** The log. */
	private static Logger LOG = LoggerFactory.getLogger(UpdateInventoryItemImpl.class);

	/** The repository. */
	private InventoryItemRepository repository;
	
	/** The query. */
	private InventoryItemQuery query;
	
	/** The user context service. */
	private AuthenticatedUserContextService userContextService;
	


	/**
	 * Instantiates a new update inventory item impl.
	 *
	 * @param repository the repository
	 * @param query the query
	 * @param userContextService the user context service
	 */
	public UpdateInventoryItemImpl(InventoryItemRepository repository,
			InventoryItemQuery query,
			AuthenticatedUserContextService userContextService){
		this.repository = repository;
		this.userContextService = userContextService;
		this.query = query;
	}
	

	@Override
	public InventoryItem execute(InventoryItem item, String refId) throws ApplicationException {
		
		UserContext user = userContextService.getAuthenticatedUser();
		
		List<InventoryItem> foundItems = query.findByExternalReferenceId(refId);
		
		if (foundItems.isEmpty()){
			throw new EntityNotFoundException(
					String.format("The inventory item with refId '%s' does not exist", refId));
		} else if (foundItems.size() > 1){
    		throw new ApplicationException(
    				String.format("More than one item with the refId %s was found!", refId));
    	}
    	
    	InventoryItem itemToUpdate = foundItems.get(0);
		
    	itemToUpdate.update(item.getDescription(), item.getPrice(), item.getQuantityInStock());
    	
		repository.save(itemToUpdate);
		
		LOG.info(String.format("User '%s' updated item %s", user.getUsername(), item.toString()));
		
		return itemToUpdate;
	}

}
