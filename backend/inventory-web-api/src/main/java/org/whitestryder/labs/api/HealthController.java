package org.whitestryder.labs.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RequestMapping(path = "/health", method = RequestMethod.GET)
    public String getHealthStatus() {
        return "healthy";
    }
	
}
