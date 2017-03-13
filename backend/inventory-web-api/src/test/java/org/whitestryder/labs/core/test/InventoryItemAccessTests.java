package org.whitestryder.labs.core.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.whitestryder.labs.core.InventoryItem;
import org.whitestryder.labs.core.InventoryItemAccess;



/**
 * Unit test for InventoryItemAccess.
 */
@RunWith(JUnit4.class)
public class InventoryItemAccessTests {

	

	/**
	 * It should require non null accessed by.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void itShouldRequireNonNullAccessedBy(){
		InventoryItem testItem = createInventoryItemForTest();
		testItem.createAccessRecord(null);
	}
	



	/**
	 * It should only allow accessed by with length no more than max allowed characters for that field.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void itShouldOnlyAllowAccessedByWithLengthNoMoreThanMaxAllowedCharactersForThatField(){
		InventoryItem testItem = createInventoryItemForTest();
		testItem.createAccessRecord("sdjskdgjs kjgskgjskjdgskljdgs jglksjdgklsjldg sdjskdgjs fiwjgmoerrig");
	}
	


	@Test
	public void itShouldAutomaticallySetDateAccessedAndInventoryItemRefIdFieldsUponConstruction(){
		InventoryItem testItem = createInventoryItemForTest();
		
		String expectedRefId = testItem.getExternalReferenceId();
		String expectedAccessedBy = "testuser";
		
		InventoryItemAccess itemAccess = testItem.createAccessRecord(expectedAccessedBy);
		
		Assert.assertEquals(expectedRefId, itemAccess.getInventoryItemRefId());
		Assert.assertEquals(expectedAccessedBy, itemAccess.getAccessedBy());
		Assert.assertNotNull(itemAccess.getDateAccessed());
	}
	
	
	
	
	/*
	 * Helpers
	 */
	
	private InventoryItem createInventoryItemForTest() {
		String testItemName = "Umbro bag";
		String testItemDesc = "Nice sports bag with lots of pockets";
		int testItemPrice = 45;
		int testItemQuantity = 5;
		InventoryItem item = new InventoryItem(testItemName, testItemDesc, testItemPrice, testItemQuantity);
		return item;
	}
	
}
