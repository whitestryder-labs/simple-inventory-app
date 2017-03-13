package org.whitestryder.labs.app.test;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.whitestryder.labs.app.support.InventoryItemAccessQuery;
import org.whitestryder.labs.app.support.InventoryItemAccessRepository;
import org.whitestryder.labs.app.support.model.InventoryItemAccessInPastMinutesAgoResult;
import org.whitestryder.labs.config.Config;
import org.whitestryder.labs.config.persistence.PersistenceConfig;
import org.whitestryder.labs.core.InventoryItem;
import org.whitestryder.labs.core.InventoryItemAccess;
import org.whitestryder.labs.support.MockWebSecurityTestConfig;



@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = {Config.class, MockWebSecurityTestConfig.class, PersistenceConfig.class})
public class InventoryItemAccessQueryTests {
	
	private static final Logger LOG = LoggerFactory.getLogger(InventoryItemAccessQueryTests.class);
	
	
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
	
	
	@Test
	public void itShouldFindInventoryItemAccessSinceDate(){	
		String testItemName = "Umbro Soccer Ball";
		int testBasePrice = 150;
		InventoryItem testItem = new InventoryItem(testItemName, "Umbro Soccer Official FIFA Approved Match Ball", testBasePrice, 10);
		
		String testExtRefId = testItem.getExternalReferenceId();

		InventoryItemAccess itemAccess1 = testItem.createAccessRecord();
		InventoryItemAccess itemAccess2 = testItem.createAccessRecord();
		
		repository.save(itemAccess1);
		repository.save(itemAccess2);
		
		Date sinceDate = Date.from(ZonedDateTime.now().minusMinutes(5).toInstant());
		
		InventoryItemAccessInPastMinutesAgoResult itemFound = query.findInventoryItemAccessedSince(sinceDate, testExtRefId);
		
		Assert.assertNotNull(itemFound);
		Assert.assertEquals(testExtRefId, itemFound.getInventoryItemRefId());
		Assert.assertEquals(2, itemFound.getCount());
		
		LOG.info(String.format("InventoryItemAccess values: refId = '%s', count = '%d'", itemFound.getInventoryItemRefId(), itemFound.getCount()));
	}
	
	
	@Test
	public void itShouldFindInventoryItemsAccessSinceDate(){	
		InventoryItem testItem1 = new InventoryItem("item a", "a description", 1, 1);

		InventoryItemAccess itemAccess1 = testItem1.createAccessRecord();
		InventoryItemAccess itemAccess2 = testItem1.createAccessRecord();
		
		
		InventoryItem testItem2 = new InventoryItem("item b", "b description", 1, 1);
		
		InventoryItemAccess itemAccess3 = testItem2.createAccessRecord();
		InventoryItemAccess itemAccess4 = testItem2.createAccessRecord();
		InventoryItemAccess itemAccess5 = testItem2.createAccessRecord();
		
		repository.save(itemAccess1);
		repository.save(itemAccess2);
		repository.save(itemAccess3);
		repository.save(itemAccess4);
		repository.save(itemAccess5);
		
		Date sinceDate = Date.from(ZonedDateTime.now().minusMinutes(5).toInstant());
		
		List<InventoryItemAccessInPastMinutesAgoResult> itemsFound = query.findInventoryItemsAccessedSince(sinceDate);
		
		Assert.assertNotNull(itemsFound);
		Assert.assertEquals(2, itemsFound.size());
		
		for (InventoryItemAccessInPastMinutesAgoResult itemResult : itemsFound) {
			LOG.info(String.format("InventoryItemAccess values: refId = '%s', count = '%d'", itemResult.getInventoryItemRefId(), itemResult.getCount()));	
		}
		
	}
	
	
	@Test
	public void itShouldFindInventoryItemsAccessSinceDate_EnsureSinceDateIsWorking(){	
		InventoryItem testItem1 = new InventoryItem("item a", "a description", 1, 1);

		InventoryItemAccess itemAccess1 = testItem1.createAccessRecord();
		
		int testDateSinceMinutes = 5;
		
		//NOTE: Hack the dateAccessed in this first itemAccess to be in the past so it doesn't get picked up in query
		ReflectionTestUtils.setField(itemAccess1, "dateAccessed", 
				Date.from(ZonedDateTime.now().minusMinutes(testDateSinceMinutes + 1).toInstant()));
		
		InventoryItemAccess itemAccess2 = testItem1.createAccessRecord();
		InventoryItemAccess itemAccess3 = testItem1.createAccessRecord();
		
		repository.save(itemAccess1);
		repository.save(itemAccess2);
		repository.save(itemAccess3);
		
		Date sinceDate = Date.from(ZonedDateTime.now().minusMinutes(testDateSinceMinutes).toInstant());
		
		List<InventoryItemAccessInPastMinutesAgoResult> itemsFound = query.findInventoryItemsAccessedSince(sinceDate);
		
		Assert.assertNotNull(itemsFound);
		
		//NOTE: the first itemAccess record should be excluded, hence only 2 found
		Assert.assertEquals(1, itemsFound.size());
		
		Assert.assertEquals(2, itemsFound.get(0).getCount());
		
		for (InventoryItemAccessInPastMinutesAgoResult itemResult : itemsFound) {
			LOG.info(String.format("InventoryItemAccess values: refId = '%s', count = '%d'", itemResult.getInventoryItemRefId(), itemResult.getCount()));	
		}
		
	}
	
	
	@Test
	public void itShouldFindNoItemsWhenInventoryItemsAccessSinceDateIsInFuture(){
		InventoryItem testItem1 = new InventoryItem("item a", "a description", 1, 1);
		InventoryItemAccess itemAccess1 = testItem1.createAccessRecord();
		
		repository.save(itemAccess1);
		
		Date sinceDate = Date.from(ZonedDateTime.now().plusHours(1).toInstant());
		
		List<InventoryItemAccessInPastMinutesAgoResult> itemsFound = query.findInventoryItemsAccessedSince(sinceDate);
		
		Assert.assertNotNull(itemsFound);
		Assert.assertEquals(0, itemsFound.size());
	}
	
	
	@Test
	public void itShouldNotFindInventoryItemsAccessSinceDateWhenNoItemsExist(){
		
		Date sinceDate = Date.from(ZonedDateTime.now().minusYears(10).toInstant());
		
		List<InventoryItemAccessInPastMinutesAgoResult> itemsFound = query.findInventoryItemsAccessedSince(sinceDate);
		
		Assert.assertNotNull(itemsFound);
		Assert.assertEquals(0, itemsFound.size());
	}
}
