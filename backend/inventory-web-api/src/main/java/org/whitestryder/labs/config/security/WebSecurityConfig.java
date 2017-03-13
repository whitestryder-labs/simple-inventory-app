package org.whitestryder.labs.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.whitestryder.labs.config.security.auth.EntryPointUnauthorizedHandler;
import org.whitestryder.labs.config.security.auth.JwtTokenAuthenticationService;
import org.whitestryder.labs.config.security.auth.StatelessAuthenticationFilter;
import org.whitestryder.labs.config.security.auth.TokenAuthenticationService;
import org.whitestryder.labs.config.security.auth.TokenHandler;
import org.whitestryder.labs.config.security.auth.UserService;

/**
 * Solution adapted from: http://technicalrex.com/2015/02/20/stateless-authentication-with-spring-security-and-jwt
 *
 */
@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	private UserService userService;
	
	private EntryPointUnauthorizedHandler unauthorizedHandler;
    
    private TokenAuthenticationService tokenAuthenticationService;
    
    private AuthenticationProvider authProvider;


    @Autowired
    public WebSecurityConfig(UserService userService, EntryPointUnauthorizedHandler unauthorizedHandler, AuthenticationProvider authProvider) {
        super(true);
        this.userService = userService;
        this.tokenAuthenticationService = new JwtTokenAuthenticationService(new TokenHandler("tooManySecrets", userService));
        this.unauthorizedHandler = unauthorizedHandler;
        this.authProvider = authProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http
        .csrf()
          .disable()
        .exceptionHandling()
          .authenticationEntryPoint(this.unauthorizedHandler)
          .and()
        .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
        .anonymous()
        .and()
        .servletApi().and()
        .authorizeRequests()
    	
	        // Allow anonymous resource requests
	        .antMatchers(HttpMethod.GET, "/health").permitAll()
	        
	        // Allow anonymous logins
	        .antMatchers(HttpMethod.POST, "/auth/token").permitAll()
	
	        // Allow access to list inventory
	        .antMatchers(HttpMethod.GET, "/api/inventory-item").permitAll()
	        
	        // All other request need to be authenticated
	        .anyRequest().authenticated().and()
		        
	        // Custom Token based authentication based on the header previously given to the client
	        .addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService),
	                UsernamePasswordAuthenticationFilter.class)
    	
	        
	        .headers().cacheControl();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    	auth.authenticationProvider(authProvider);
    }
    
    

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return this.userService;
    }


    @Bean
    public TokenAuthenticationService tokenAuthenticationService() {
        return this.tokenAuthenticationService;
    }
    
    
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("sbrooke").password("password").roles("USER")
//                .and()
//                .withUser("amagi").password("password").roles("USER");
//    }
}