package org.whitestryder.labs.app.activity.inventory;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.whitestryder.labs.app.support.ApplicationException;
import org.whitestryder.labs.app.support.EntityNotFoundException;
import org.whitestryder.labs.app.support.InventoryItemAccessRepository;
import org.whitestryder.labs.app.support.InventoryItemQuery;
import org.whitestryder.labs.core.InventoryItem;
import org.whitestryder.labs.core.InventoryItemAccess;



/**
 * Provides a way to get a single inventory item and record each access.
 */
public class GetSingleInventoryItemImpl implements GetSingleInventoryItem {

	/** The log. */
	private static Logger LOG = LoggerFactory.getLogger(GetSingleInventoryItemImpl.class);
	
	/** The inventory item access repository. */
	private InventoryItemAccessRepository repository;
	
	/** The inventory item query service . */
	private InventoryItemQuery query;
	
	


	/**
	 * Instantiates a new gets the single inventory item impl.
	 *
	 * @param repository the repository
	 * @param query the query
	 */
	public GetSingleInventoryItemImpl(
			InventoryItemAccessRepository repository,
			InventoryItemQuery query){
		this.repository = repository;
		this.query = query;
	}
	
	
	@Override
	public InventoryItem execute(String refId) throws ApplicationException {
				
		List<InventoryItem> foundItems = query.findByExternalReferenceId(refId);
    	
    	if (foundItems.isEmpty()){
    		throw new EntityNotFoundException(
					String.format("The inventory item with refId '%s' does not exist", refId));
    	} else if (foundItems.size() > 1){
    		throw new ApplicationException(
    				String.format("More than one item with the refId %s was found!", refId));
    	}
    	
    	InventoryItem item = foundItems.get(0);
    	
    	InventoryItemAccess iiAccess = item.createAccessRecord();
		
		repository.save(iiAccess);
		
		LOG.info(String.format("Inventory item with refId '%s' accessed by '%s'", refId, iiAccess.getAccessedBy()));
		
		return item;
	}

}
