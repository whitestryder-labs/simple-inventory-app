package org.whitestryder.labs.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.whitestryder.labs.core.InventoryItem;

import org.junit.Assert;



/**
 * Unit test for InventoryItem.
 */
@RunWith(JUnit4.class)
public class InventoryItemTests {

	
	/**
	 * It should require non null name.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void itShouldRequireNonNullName(){
		new InventoryItem(null, null, 0, 0);
	}
	
	/**
	 * It should require non empty name.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void itShouldRequireNonEmptyName(){
		new InventoryItem("", null, 0, 0);
	}
	
	
	/**
	 * It should require price greater than or equal to zero.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void itShouldRequirePriceGreaterThanOrEqualToZero(){
		new InventoryItem("my item", null, -1, 0);
	}
	
	/**
	 * It should require quantity in stock greater than or equal to zero.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void itShouldRequireQuantityInStockGreaterThanOrEqualToZero(){
		new InventoryItem("my item", null, 5, -1);
	}
	
	/**
	 * It should only allow name with length no more than max allowed characters for name.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void itShouldOnlyAllowNameWithLengthNoMoreThanMaxAllowedCharactersForName(){
		new InventoryItem("This is a really long name, it is so long that it doesn't fit", null, 5, -1);
	}
	
	/**
	 * It should only allow name with length no more than max allowed characters for description.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void itShouldOnlyAllowNameWithLengthNoMoreThanMaxAllowedCharactersForDescription(){
		new InventoryItem("my item",
				"This is a really long description, it is so long that it doesn't fit.  How could we make this description shorter? could we remove certain words?  Maybe not, removing certain words from a sentence can change the entire meaning of the sentence.  For example ...",
				5, -1);
	}
	
	
	/**
	 * It should allow creation with empty description.
	 */
	@Test
	public void itShouldAllowCreationWithEmptyDescription(){
		String testItemName = "Addidas bag";
		String testItemDesc = "";
		int testItemPrice = 25;
		int testItemQuantity = 10;
		InventoryItem item = new InventoryItem(testItemName, testItemDesc, testItemPrice, testItemQuantity);
		
		Assert.assertEquals(testItemName, item.getName());
		Assert.assertEquals(testItemDesc, item.getDescription());
		Assert.assertEquals(testItemPrice, item.getPrice());
		Assert.assertEquals(testItemQuantity, item.getQuantityInStock());
	}
	
	
	/**
	 * It should allow creation with non empty description.
	 */
	@Test
	public void itShouldAllowCreationWithNonEmptyDescription(){
		String testItemName = "Umbro bag";
		String testItemDesc = "Nice sports bag with lots of pockets";
		int testItemPrice = 45;
		int testItemQuantity = 5;
		InventoryItem item = new InventoryItem(testItemName, testItemDesc, testItemPrice, testItemQuantity);
		
		Assert.assertEquals(testItemName, item.getName());
		Assert.assertEquals(testItemDesc, item.getDescription());
		Assert.assertEquals(testItemPrice, item.getPrice());
		Assert.assertEquals(testItemQuantity, item.getQuantityInStock());
	}
	
	
	/**
	 * It should provide ability to buy item.
	 */
	@Test
	public void itShouldProvideAbilityToBuyItem(){
		int testItemQuantity = 10;
		InventoryItem item = new InventoryItem("An item", null, 25, testItemQuantity);
			
		item.buy();
		
		int expectedQuantityInStock = testItemQuantity - 1;
		Assert.assertEquals(expectedQuantityInStock, item.getQuantityInStock());
	}
	
	
	@Test(expected = IllegalStateException.class)
	public void itShouldPreventBuyingOfItemIfNoneInStock(){
		int testItemQuantity = 1;
		InventoryItem item = new InventoryItem("An item", null, 25, testItemQuantity);
			
		item.buy();	//Buy the last item	
		item.buy(); //This operation will not be allowed.
	}
	
}
