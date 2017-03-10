package org.whitestryder.labs.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

    
    @RequestMapping(path = "/api/inventory/{id}/purchase", method = RequestMethod.POST)
    public String buyItemFromInventory(@PathVariable String id) {
        return String.format("You requested to purchase item with id=%s", id);
    }
    
    @RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
    public String getInventory() {
        return "Inventory TBD";
    }
    
    @RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
    public String getInventoryItem(@PathVariable String id) {
        return "Inventory Item " + id;
    }
}
