package org.whitestryder.labs.app.support;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.whitestryder.labs.app.support.model.InventoryItemBasePriceResult;
import org.whitestryder.labs.core.InventoryItem;

@Repository
public interface InventoryItemQuery extends PagingAndSortingRepository<InventoryItem, Long> {

	List<InventoryItem> findByName(@Param("name") String name);
	
	List<InventoryItem> findAll();
	
	List<InventoryItem> findByExternalReferenceId(String externalReferenceId);

	
	@Query(
	  "SELECT externalReferenceId as refId,  price as basePrice FROM InventoryItem WHERE externalReferenceId IN :refIds")
	List<InventoryItemBasePriceResult> findInventoryItemBasePricesForItemRefIds(@Param("refIds") List<String> refIds);
}
