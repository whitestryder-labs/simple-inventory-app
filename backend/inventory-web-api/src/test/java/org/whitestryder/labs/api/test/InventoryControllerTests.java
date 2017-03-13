package org.whitestryder.labs.api.test;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.whitestryder.labs.api.model.InventoryItems;
import org.whitestryder.labs.support.MockWebSecurityTestConfig;


/**
 * Provide an integration test for the REST API /inventory-items resource
 * @author steve
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(MockWebSecurityTestConfig.class)
@TestPropertySource(locations="classpath:test.properties")
public class InventoryControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    
    @Before
    public void setup(){
    	SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    	requestFactory.setOutputStreaming(false);
    	restTemplate.getRestTemplate().setRequestFactory(requestFactory);
    }
    
    
    
    /**
     * It should list inventory items.
     */
    @Test
    public void itShouldListZeroInventoryItemsWhenNoneCreated() {        
        ResponseEntity<InventoryItems> responseEntity =
        	restTemplate.getForEntity("/api/inventory-item", InventoryItems.class);
      
        InventoryItems items = responseEntity.getBody();
        
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(items);
        Assert.assertEquals(0, items.getItems().size());
    }
    
    
    /**
     * It should list inventory items.
     */
    @Test
    public void itShouldListInventoryItems() {
    	InventoryItemForm itemForm1 = new InventoryItemForm("a", "b", 1, 2);
    	ResponseEntity<String> responseFromCreate = 
    			restTemplate.postForEntity("/api/inventory-item", itemForm1, String.class);
    	
        Assert.assertEquals(HttpStatus.CREATED,  responseFromCreate.getStatusCode());
        String locationHeader = responseFromCreate.getHeaders().getLocation().toString();
        Assert.assertNotNull(locationHeader);
        
        ResponseEntity<InventoryItems> responseEntity =
        	restTemplate.getForEntity("/api/inventory-item", InventoryItems.class);
      
        InventoryItems items = responseEntity.getBody();
        
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assert.assertNotNull(items);
        Assert.assertEquals(1, items.getItems().size());
    }
    
    
    
    
    
    
    class InventoryItemForm implements Serializable {
    	/**
		 * generated
		 */
		private static final long serialVersionUID = 8657484601296859057L;
		
		public String name;
    	public String description;
    	public int price;
    	public int quantityInStock;	
		
		
		public InventoryItemForm() {
		}
		
		
		public InventoryItemForm(String name, String description, int price, int quantityInStock) {
			super();
			this.name = name;
			this.description = description;
			this.price = price;
			this.quantityInStock = quantityInStock;
		}

    }
}
