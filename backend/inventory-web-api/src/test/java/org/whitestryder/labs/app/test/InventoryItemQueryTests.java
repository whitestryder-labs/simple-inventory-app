package org.whitestryder.labs.app.test;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.whitestryder.labs.app.support.InventoryItemQuery;
import org.whitestryder.labs.app.support.InventoryItemRepository;
import org.whitestryder.labs.config.Config;
import org.whitestryder.labs.config.persistence.PersistenceConfig;
import org.whitestryder.labs.core.InventoryItem;
import org.whitestryder.labs.support.MockWebSecurityTestConfig;



@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = {Config.class, MockWebSecurityTestConfig.class, PersistenceConfig.class})
public class InventoryItemQueryTests {
	
	@Autowired
	private InventoryItemQuery query;
	
	@Autowired
	private InventoryItemRepository repository;
	
	@Autowired
	private EntityManager entityManager;
	
	
	/**
	 * It should return no items when no items exist find by name.
	 */
	@Test
	public void itShouldReturnNoItemsWhenNoItemsExistFindByName(){	
		List<InventoryItem> itemsFound = query.findAll();
		
		Assert.assertNotNull(itemsFound);
		Assert.assertEquals(0,  itemsFound.size());
	}
	
	
	/**
	 * It should find by name.
	 */
	@Test
	public void itShouldFindByName(){	
		String testItemName = "Umbro Soccer Ball";
		
		InventoryItem testItem = new InventoryItem(testItemName, "Umbro Soccer Official FIFA Approved Match Ball", 150, 10);
		
		repository.save(testItem);
		
		List<InventoryItem> itemsFound = query.findByName( testItemName );
		
		Assert.assertNotNull(itemsFound);
		Assert.assertEquals(1,  itemsFound.size());
	}
	
	
	/**
	 * It should not find by name when item no item with that name.
	 */
	@Test
	public void itShouldNotFindByNameWhenItemNoItemWithThatName(){	
		String testItemName = "Nike CR7";
		
		InventoryItem testItem = new InventoryItem(testItemName, "Nike Christiano Ronaldo soccer shoes special edition", 250, 2);
		
		repository.save(testItem);
		
		List<InventoryItem> itemsFound = query.findByName( testItemName + " this item won't be found" );
		
		Assert.assertNotNull(itemsFound);
		Assert.assertEquals(0,  itemsFound.size());
	}
	
}
