package org.whitestryder.labs.api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.whitestryder.labs.api.model.InventoryItems;
import org.whitestryder.labs.api.model.LinkRelType;
import org.whitestryder.labs.api.model.RootApiRepresentation;
import org.whitestryder.labs.app.support.ApplicationException;

import com.google.common.collect.Lists;



@RestController
public class RootController {

    @RequestMapping(path = "/api", method = RequestMethod.GET)
    public ResponseEntity<RootApiRepresentation> listApiResources() throws ApplicationException {
        
    	InventoryItems invItems = new InventoryItems(Lists.newArrayList());
    	
    	
    	
    	RootApiRepresentation rootRep = new RootApiRepresentation();
    	rootRep.add(invItems.getLinks());
    	rootRep.add(linkTo(methodOn(AuthController.class).authenticationRequest(null, null)).withRel(LinkRelType.X_AUTH_LOGIN.getCode()));
    	
    	return ResponseEntity.ok().body(rootRep);
    }
	
}

