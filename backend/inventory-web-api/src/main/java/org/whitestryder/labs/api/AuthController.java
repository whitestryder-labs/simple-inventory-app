package org.whitestryder.labs.api;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.whitestryder.labs.api.model.AuthenticationRequest;
import org.whitestryder.labs.config.security.auth.TokenAuthenticationService;
import org.whitestryder.labs.config.security.auth.UserAuthentication;
import org.whitestryder.labs.config.security.auth.UsernamePasswordAuthentication;

@RestController
public class AuthController {

	private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;
	
	
	
	@RequestMapping(path = "/auth/token", method = RequestMethod.POST)
	public ResponseEntity<?> authenticationRequest(
			@RequestBody AuthenticationRequest authenticationRequest,
			HttpServletResponse response) {

	    // Perform the authentication
		UserAuthentication authentication = null;
		try {
		    authentication = (UserAuthentication)this.authenticationManager.authenticate(
		      new UsernamePasswordAuthentication(
		        authenticationRequest.getUsername(),
		        authenticationRequest.getPassword()
		      )
		    );
		    SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e){
			LOG.error(String.format("Authentication failed, reason: %s", e.getMessage(), e));
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
		}
		
		if (authentication == null){
			LOG.error("Authentication failed, unknown reason");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
		}

	    // Generate the token
	    this.tokenAuthenticationService.addAuthentication(response, authentication);

	    // Return the token
	    return ResponseEntity.ok("Authenticated");
	}
	
	
	
}
