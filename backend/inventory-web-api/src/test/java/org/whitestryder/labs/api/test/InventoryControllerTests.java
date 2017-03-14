package org.whitestryder.labs.api.test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.whitestryder.labs.api.model.InventoryItemPurchaseResult;
import org.whitestryder.labs.api.model.InventoryItemRepresentation;
import org.whitestryder.labs.api.model.InventoryItems;
import org.whitestryder.labs.app.support.InventoryItemAccessQuery;
import org.whitestryder.labs.app.support.InventoryItemQuery;
import org.whitestryder.labs.core.InventoryItem;
import org.whitestryder.labs.core.InventoryItemAccess;
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
    
    
    @Autowired
    private InventoryItemQuery iiQuery;
    
    @Autowired
    private InventoryItemAccessQuery iiaQuery;

    
    @Before
    public void setup(){
    	SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    	requestFactory.setOutputStreaming(false);
    	restTemplate.getRestTemplate().setRequestFactory(requestFactory);
    }
    
    
    

    @Test
    public void runRestApiTests() {
    	
    	itShouldListZeroInventoryItemsWhenNoneCreated();
    	
    	itShouldListInventoryItems();
    	
    	itShouldGetSingleInventoryItemAndRecordAccess();
    	
    	itShouldAllowAnInStockIventoryItemToBeBought();
    	
    	itShouldPreventOutOfStockIventoryItemFromBeingBought();
    	
    	itShouldAllowAnIventoryItemToBeUpdated();
    }
    
    
    
    /**
     * It should list inventory items.
     */
    private void itShouldListZeroInventoryItemsWhenNoneCreated() {  
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
    private void itShouldListInventoryItems() {
    	InventoryItemForm itemForm1 = new InventoryItemForm(UUID.randomUUID().toString(), "b", 1, 2);
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
    
    

    private void itShouldGetSingleInventoryItemAndRecordAccess() {
    	InventoryItemForm itemForm1 = new InventoryItemForm(UUID.randomUUID().toString(), "b", 1, 2);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<InventoryItemForm> entity = new HttpEntity<InventoryItemForm>(itemForm1, headers);
    	ResponseEntity<String> responseFromCreate = restTemplate.exchange("/api/inventory-item", HttpMethod.POST, entity, String.class);
   	
        Assert.assertEquals(HttpStatus.CREATED,  responseFromCreate.getStatusCode());
        String urlOfItemCreated = responseFromCreate.getHeaders().getLocation().toString();
        Assert.assertNotNull(urlOfItemCreated);
        
        HttpHeaders getHeaders = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	HttpEntity<InventoryItemRepresentation> getEntity = new HttpEntity<InventoryItemRepresentation>(getHeaders);
    	ResponseEntity<InventoryItemRepresentation> responseEntityFromGet = 
    			restTemplate.exchange(urlOfItemCreated, HttpMethod.GET, getEntity, InventoryItemRepresentation.class);
      
        InventoryItemRepresentation item = responseEntityFromGet.getBody();
        
        Assert.assertEquals(HttpStatus.OK, responseEntityFromGet.getStatusCode());
        Assert.assertNotNull(item);
        
        List<InventoryItemAccess> itemAccesses = iiaQuery.findByInventoryItemRefId(item.getExternalReferenceId());
        
        Assert.assertEquals(1, itemAccesses.size());
    }
    
    
    private void itShouldAllowAnInStockIventoryItemToBeBought() {
    	int testQuantityInStock = 2;
    	InventoryItemForm itemForm1 = new InventoryItemForm(UUID.randomUUID().toString(), "b", 1, testQuantityInStock);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<InventoryItemForm> entity = new HttpEntity<InventoryItemForm>(itemForm1, headers);
    	ResponseEntity<String> responseFromCreate = restTemplate.exchange("/api/inventory-item", HttpMethod.POST, entity, String.class);
   	
        Assert.assertEquals(HttpStatus.CREATED,  responseFromCreate.getStatusCode());
        String urlOfItemCreated = responseFromCreate.getHeaders().getLocation().toString();
        Assert.assertNotNull(urlOfItemCreated);
        
        HttpHeaders getHeaders = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	HttpEntity<InventoryItemRepresentation> getEntity = new HttpEntity<InventoryItemRepresentation>(getHeaders);
    	ResponseEntity<InventoryItemRepresentation> responseEntityFromGet = 
    			restTemplate.exchange(urlOfItemCreated, HttpMethod.GET, getEntity, InventoryItemRepresentation.class);
      
        InventoryItemRepresentation item = responseEntityFromGet.getBody();
        
        Assert.assertEquals(HttpStatus.OK, responseEntityFromGet.getStatusCode());
        Assert.assertNotNull(item);
        
    	HttpHeaders buyOpHeaders = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<InventoryItemForm> buyOpEntity = new HttpEntity<InventoryItemForm>(buyOpHeaders);
    	String urlToBuyItem = "/api/inventory-item/" + item.getExternalReferenceId() + "/purchase";
    	ResponseEntity<InventoryItemPurchaseResult> responseFromBuyItem = 
    			restTemplate.exchange(urlToBuyItem, HttpMethod.POST, buyOpEntity, InventoryItemPurchaseResult.class);
        
    	Assert.assertEquals(HttpStatus.OK, responseFromBuyItem.getStatusCode());
    	
        //Check quanityInStock decreased
        List<InventoryItem> itemsFound = iiQuery.findByExternalReferenceId(item.getExternalReferenceId());
        Assert.assertEquals(1, itemsFound.size());        
        InventoryItem itemBought = itemsFound.get(0);
        
        Assert.assertEquals(testQuantityInStock - 1, itemBought.getQuantityInStock());
    }
    
    
    private void itShouldPreventOutOfStockIventoryItemFromBeingBought() {
    	int testQuantityInStock = 0;
    	InventoryItemForm itemForm1 = new InventoryItemForm(UUID.randomUUID().toString(), "b", 1, testQuantityInStock);
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<InventoryItemForm> entity = new HttpEntity<InventoryItemForm>(itemForm1, headers);
    	ResponseEntity<String> responseFromCreate = restTemplate.exchange("/api/inventory-item", HttpMethod.POST, entity, String.class);
   	
        Assert.assertEquals(HttpStatus.CREATED,  responseFromCreate.getStatusCode());
        String urlOfItemCreated = responseFromCreate.getHeaders().getLocation().toString();
        Assert.assertNotNull(urlOfItemCreated);
        
        HttpHeaders getHeaders = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	HttpEntity<InventoryItemRepresentation> getEntity = new HttpEntity<InventoryItemRepresentation>(getHeaders);
    	ResponseEntity<InventoryItemRepresentation> responseEntityFromGet = 
    			restTemplate.exchange(urlOfItemCreated, HttpMethod.GET, getEntity, InventoryItemRepresentation.class);
      
        InventoryItemRepresentation item = responseEntityFromGet.getBody();
        
        Assert.assertEquals(HttpStatus.OK, responseEntityFromGet.getStatusCode());
        Assert.assertNotNull(item);
        
    	HttpHeaders buyOpHeaders = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<InventoryItemForm> buyOpEntity = new HttpEntity<InventoryItemForm>(buyOpHeaders);
    	String urlToBuyItem = "/api/inventory-item/" + item.getExternalReferenceId() + "/purchase";
    	ResponseEntity<InventoryItemPurchaseResult> responseFromBuyItem = 
    			restTemplate.exchange(urlToBuyItem, HttpMethod.POST, buyOpEntity, InventoryItemPurchaseResult.class);
        
    	Assert.assertEquals(HttpStatus.CONFLICT, responseFromBuyItem.getStatusCode());
    }
    
    
    
    private void itShouldAllowAnIventoryItemToBeUpdated() {
    	int testQuantityInStock = 2;
    	InventoryItemForm itemForm1 = new InventoryItemForm(UUID.randomUUID().toString(), "b", 1, testQuantityInStock);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<InventoryItemForm> entity = new HttpEntity<InventoryItemForm>(itemForm1, headers);
    	ResponseEntity<String> responseFromCreate = restTemplate.exchange("/api/inventory-item", HttpMethod.POST, entity, String.class);
   	
        Assert.assertEquals(HttpStatus.CREATED,  responseFromCreate.getStatusCode());
        String urlOfItemCreated = responseFromCreate.getHeaders().getLocation().toString();
        Assert.assertNotNull(urlOfItemCreated);
        
        String updatedDesc = "b - updated";
        int updatedPrice = 2;
        int updatedQuantity = ++testQuantityInStock;
        HttpHeaders putHeaders = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	InventoryItemUpdateForm updatedItem = new InventoryItemUpdateForm(updatedDesc, updatedPrice, updatedQuantity);
    	HttpEntity<InventoryItemUpdateForm> putEntity = new HttpEntity<InventoryItemUpdateForm>(updatedItem, putHeaders);
    	ResponseEntity<InventoryItemRepresentation> responseEntityFromPut = 
    			restTemplate.exchange(urlOfItemCreated, HttpMethod.PUT, putEntity, InventoryItemRepresentation.class);
        
        Assert.assertEquals(HttpStatus.OK, responseEntityFromPut.getStatusCode());

        HttpHeaders getHeaders = new HttpHeaders();
    	headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    	HttpEntity<InventoryItemRepresentation> getEntity = new HttpEntity<InventoryItemRepresentation>(getHeaders);
    	ResponseEntity<InventoryItemRepresentation> responseEntityFromGet = 
    			restTemplate.exchange(urlOfItemCreated, HttpMethod.GET, getEntity, InventoryItemRepresentation.class);
      
        InventoryItemRepresentation retrievedItem = responseEntityFromGet.getBody();
        
        //Check updates stuck
        Assert.assertEquals(updatedDesc, retrievedItem.getDescription());
        Assert.assertEquals(updatedPrice*1.0, retrievedItem.getPrice(), 0.001);
        Assert.assertEquals(updatedQuantity, retrievedItem.getQuantityInStock());
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
    
    
    
    class InventoryItemUpdateForm implements Serializable {
    	/**
		 * generated
		 */

		
    	public String description;
    	public int price;
    	public int quantityInStock;	
		
		
		public InventoryItemUpdateForm() {
		}
		
		
		public InventoryItemUpdateForm(String description, int price, int quantityInStock) {
			super();
			this.description = description;
			this.price = price;
			this.quantityInStock = quantityInStock;
		}

    }
}
