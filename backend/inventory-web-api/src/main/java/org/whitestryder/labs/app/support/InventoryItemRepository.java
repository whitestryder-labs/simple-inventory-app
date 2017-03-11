package org.whitestryder.labs.app.support;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.whitestryder.labs.core.InventoryItem;

@Repository
public interface InventoryItemRepository extends CrudRepository<InventoryItem, Long> {

	

}
