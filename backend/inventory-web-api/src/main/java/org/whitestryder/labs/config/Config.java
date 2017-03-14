package org.whitestryder.labs.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.whitestryder.labs.app.activity.inventory.CreateInventoryItem;
import org.whitestryder.labs.app.activity.inventory.CreateInventoryItemImpl;
import org.whitestryder.labs.app.activity.inventory.GetInventoryItemList;
import org.whitestryder.labs.app.activity.inventory.GetInventoryItemListImpl;
import org.whitestryder.labs.app.activity.inventory.GetSingleInventoryItem;
import org.whitestryder.labs.app.activity.inventory.GetSingleInventoryItemImpl;
import org.whitestryder.labs.app.support.AuthenticatedUserContextService;
import org.whitestryder.labs.app.support.InventoryItemAccessQuery;
import org.whitestryder.labs.app.support.InventoryItemAccessRepository;
import org.whitestryder.labs.app.support.InventoryItemQuery;
import org.whitestryder.labs.app.support.InventoryItemRepository;
import org.whitestryder.labs.app.support.pricing.InventoryItemSurgePricingModel;
import org.whitestryder.labs.app.support.pricing.PricingModel;
import org.whitestryder.labs.config.security.auth.CustomAuthenticationProvider;
import org.whitestryder.labs.config.security.auth.EntryPointUnauthorizedHandler;
import org.whitestryder.labs.config.security.auth.UserService;

/**
 * Defined bean implementations for dependency injection
 * @author steve
 *
 */
@Configuration
@Order(1)
public class Config {
	
	@Autowired
	private InventoryItemRepository inventoryItemRepository;

	@Autowired
	private InventoryItemQuery inventoryItemQuery;
	
	@Autowired
	private InventoryItemAccessRepository inventoryItemAccessRepository;
	
	@Autowired
	private InventoryItemAccessQuery inventoryItemAccessQuery;
	
	@Autowired
	private AuthenticatedUserContextService authUserContextService;
	
	@Bean
	public UserService userService(){
		UserService service = new UserService();
		
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		User user1 = new User("sbrooke", "password", grantedAuthorities);
		User user2 = new User("amagi", "password", grantedAuthorities);
		service.addUser(user1);
		service.addUser(user2);
		
		return service;
	}
	
	@Bean
	public EntryPointUnauthorizedHandler unauthorizedHandler(){
		return new EntryPointUnauthorizedHandler();
	}
	
	@Bean
	public AuthenticationProvider authProvider(UserService userService){
		CustomAuthenticationProvider provider = new CustomAuthenticationProvider(userService);
		return provider;
	}
	
	
	@Bean
	public CreateInventoryItem createInventoryItem(){
		return new CreateInventoryItemImpl(
				inventoryItemRepository, inventoryItemQuery, authUserContextService);
	}
	
	

	@Bean
	public PricingModel surgePricingModel(){
		return new InventoryItemSurgePricingModel(inventoryItemAccessQuery, inventoryItemQuery);
	}
	
	
	@Bean
	public GetSingleInventoryItem getSingleInventoryItem(PricingModel pricingModel){
		return new GetSingleInventoryItemImpl(
				inventoryItemAccessRepository, inventoryItemQuery, pricingModel);
	}



	
	@Bean
	public GetInventoryItemList getInventoryItemList(PricingModel pricingModel){
		return new GetInventoryItemListImpl(inventoryItemQuery, pricingModel);
	}
	
	
}
