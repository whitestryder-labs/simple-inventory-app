package org.whitestryder.labs.app.support;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.whitestryder.labs.core.InventoryItem;

@Repository
public interface InventoryItemQuery extends PagingAndSortingRepository<InventoryItem, Long> {

	List<InventoryItem> findByName(@Param("name") String name);
	
	List<InventoryItem> findAll();

}
