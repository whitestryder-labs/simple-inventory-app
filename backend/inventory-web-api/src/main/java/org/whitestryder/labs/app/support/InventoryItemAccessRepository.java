package org.whitestryder.labs.app.support;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.whitestryder.labs.core.InventoryItemAccess;

@Repository
public interface InventoryItemAccessRepository extends CrudRepository<InventoryItemAccess, Long> {

	

}
