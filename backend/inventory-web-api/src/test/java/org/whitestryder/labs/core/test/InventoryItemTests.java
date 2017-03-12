package org.whitestryder.labs.core.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.whitestryder.labs.core.InventoryItem;

@RunWith(JUnit4.class)
public class InventoryItemTests {

	
	@Test(expected = IllegalArgumentException.class)
	public void itShouldRequireNonNullName(){
		new InventoryItem(null, null, 0, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void itShouldRequireNonEmptyName(){
		new InventoryItem("", null, 0, 0);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void itShouldRequirePriceGreaterThanOrEqualToZero(){
		new InventoryItem("my item", null, -1, 0);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void itShouldRequireQuantityInStockGreaterThanOrEqualToZero(){
		new InventoryItem("my item", null, 5, -1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void itShouldOnlyAllowNameWithLengthNoMoreThanMaxAllowedCharactersForName(){
		new InventoryItem("This is a really long name, it is so long that it doesn't fit", null, 5, -1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void itShouldOnlyAllowNameWithLengthNoMoreThanMaxAllowedCharactersForDescription(){
		new InventoryItem("my item",
				"This is a really long description, it is so long that it doesn't fit.  How could we make this description shorter? could we remove certain words?  Maybe not, removing certain words from a sentence can change the entire meaning of the sentence.  For example ...",
				5, -1);
	}
	
}
