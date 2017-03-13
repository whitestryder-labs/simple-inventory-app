package org.whitestryder.labs.config.security.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.whitestryder.labs.app.support.AuthenticatedUserContextService;

@Configuration
@Profile("!test")
public class AuthConfig {


	@Bean
	public AuthenticatedUserContextService authUserContextService(){
		return new AuthenticatedUserContextServiceImpl();
	}
	
}
