package org.whitestryder.labs.app.support;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.whitestryder.labs.app.support.model.InventoryItemAccessInPastMinutesAgoResult;
import org.whitestryder.labs.core.InventoryItemAccess;

@Repository
public interface InventoryItemAccessQuery extends PagingAndSortingRepository<InventoryItemAccess, Long> {

	List<InventoryItemAccess> findAll();
	
	List<InventoryItemAccess> findByInventoryItemRefId(String inventoryItemRefId);

	
	@Query(
	  "SELECT inventoryItemRefId as inventoryItemRefId,  COUNT(id) as count FROM InventoryItemAccess WHERE dateAccessed > :sinceDate AND inventoryItemRefId = :refId")
	InventoryItemAccessInPastMinutesAgoResult findInventoryItemAccessedSince(@Param("sinceDate") Date sinceDate, @Param("refId") String refId);
	
	
	@Query(
	  "SELECT inventoryItemRefId as inventoryItemRefId,  COUNT(id) as count FROM InventoryItemAccess WHERE dateAccessed > :sinceDate GROUP BY inventoryItemRefId")
	List<InventoryItemAccessInPastMinutesAgoResult> findInventoryItemsAccessedSince(@Param("sinceDate") Date sinceDate);
}
