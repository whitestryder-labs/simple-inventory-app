package org.whitestryder.labs.app.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.whitestryder.labs.app.support.InventoryItemAccessQuery;
import org.whitestryder.labs.app.support.InventoryItemAccessRepository;
import org.whitestryder.labs.config.Config;
import org.whitestryder.labs.config.persistence.PersistenceConfig;
import org.whitestryder.labs.core.InventoryItem;
import org.whitestryder.labs.core.InventoryItemAccess;
import org.whitestryder.labs.support.MockWebSecurityTestConfig;



@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = {Config.class, MockWebSecurityTestConfig.class, PersistenceConfig.class})
public class InventoryItemAccessQueryTests {
	
	@Autowired
	private InventoryItemAccessQuery query;
	
	@Autowired
	private InventoryItemAccessRepository repository;
	
	

	
	
	/**
	 * It should return no items when no items exist find by name.
	 */
	@Test
	public void itShouldReturnNoItemsWhenNoItemsExistFindByName(){	
		List<InventoryItemAccess> itemsFound = query.findAll();
		
		Assert.assertNotNull(itemsFound);
		Assert.assertEquals(0,  itemsFound.size());
	}
	
	
	@Test
	public void itShouldFindByInventoryItemExtRefId(){	
		String testItemName = "Umbro Soccer Ball";
		InventoryItem testItem = new InventoryItem(testItemName, "Umbro Soccer Official FIFA Approved Match Ball", 150, 10);
		
		String testExtRefId = testItem.getExternalReferenceId();
		
		String testAccessedBy = "testuser";
		InventoryItemAccess itemAccess = testItem.createAccessRecord(testAccessedBy);
		
		repository.save(itemAccess);
		
		List<InventoryItemAccess> itemsFound = query.findByInventoryItemRefId(testExtRefId);
		
		Assert.assertNotNull(itemsFound);
		Assert.assertEquals(1,  itemsFound.size());
		Assert.assertEquals(testExtRefId, itemsFound.get(0).getInventoryItemRefId());
	}
	
	

	
}
