package org.whitestryder.labs.app.test;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.whitestryder.labs.app.support.InventoryItemAccessRepository;
import org.whitestryder.labs.app.support.InventoryItemRepository;
import org.whitestryder.labs.app.support.pricing.InventoryItemSurgePricingModel;
import org.whitestryder.labs.app.support.pricing.ItemPrice;
import org.whitestryder.labs.app.support.pricing.PricingModel;
import org.whitestryder.labs.config.Config;
import org.whitestryder.labs.config.persistence.PersistenceConfig;
import org.whitestryder.labs.core.InventoryItem;
import org.whitestryder.labs.core.InventoryItemAccess;
import org.whitestryder.labs.support.MockWebSecurityTestConfig;


/**
 * The Class InventoryItemSurgePricingModelTests.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = {Config.class, MockWebSecurityTestConfig.class, PersistenceConfig.class})
public class InventoryItemSurgePricingModelTests {

	/** The ii repository. */
	@Autowired
	private InventoryItemRepository iiRepository;
	
	/** The iia repository. */
	@Autowired
	private InventoryItemAccessRepository iiaRepository;
	
	/** The pricing model. */
	@Autowired
	private PricingModel pricingModel;
	
	
	/**
	 * Setup.
	 */
	@Before
	public void setup(){
		Assert.assertTrue(pricingModel instanceof InventoryItemSurgePricingModel);
	}
	
	/**
	 * It should return empty surge price map when no surge present.
	 */
	@Test
	public void itShouldReturnEmptySurgePriceMapWhenNoSurgePresent(){
		double basePrice = 10.0;
		
		InventoryItem testItem1 = new InventoryItem("item a", "a description", (int)basePrice, 1);
		
		iiRepository.save(testItem1);
		
		int numItemAccesses = ((InventoryItemSurgePricingModel)pricingModel).getSurgePriceModelViewsPerAccessTimeThreshold();
		List<InventoryItemAccess> itemAccess = Lists.newArrayList();
		for (int i=0; i < numItemAccesses-1; i++){
			InventoryItemAccess ia = testItem1.createAccessRecord();
			itemAccess.add(ia);
			iiaRepository.save(ia);
		}

		
		Map<String, ItemPrice> pricingMap = pricingModel.calcPrices();
		
		Assert.assertTrue(pricingMap.isEmpty());
	}

	
	/**
	 * It should return price increase when surge present.
	 */
	@Test
	public void itShouldReturnPriceIncreaseWhenSurgePresent(){
		double basePrice = 10.0;
		double priceDiffTolerance = 0.001;
		
		InventoryItem testItem1 = new InventoryItem("item a", "a description", (int)basePrice, 1);
		
		iiRepository.save(testItem1);
		
		String testInventoryItemRefId = testItem1.getExternalReferenceId();
		
		int numItemAccesses = ((InventoryItemSurgePricingModel)pricingModel).getSurgePriceModelViewsPerAccessTimeThreshold();
		List<InventoryItemAccess> itemAccess = Lists.newArrayList();
		for (int i=0; i < numItemAccesses; i++){
			InventoryItemAccess ia = testItem1.createAccessRecord();
			itemAccess.add(ia);
			iiaRepository.save(ia);
		}

		
		Map<String, ItemPrice> pricingMap = pricingModel.calcPrices();
		
		//NOTE: This test assumes that the surge price percent increase is 10%, it is
		//hard-coded here, if we change the configuration of the pricing model this test will need to change.
		Assert.assertTrue(pricingMap.containsKey(testInventoryItemRefId));
		Assert.assertEquals(basePrice + basePrice * 0.10, pricingMap.get(testInventoryItemRefId).getPrice(), priceDiffTolerance);
		
	}
}
