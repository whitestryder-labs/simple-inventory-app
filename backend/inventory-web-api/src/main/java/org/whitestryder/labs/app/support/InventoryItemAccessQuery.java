package org.whitestryder.labs.app.support;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.whitestryder.labs.core.InventoryItemAccess;

@Repository
public interface InventoryItemAccessQuery extends PagingAndSortingRepository<InventoryItemAccess, Long> {

	List<InventoryItemAccess> findAll();
	
	List<InventoryItemAccess> findByInventoryItemRefId(String inventoryItemRefId);

}
