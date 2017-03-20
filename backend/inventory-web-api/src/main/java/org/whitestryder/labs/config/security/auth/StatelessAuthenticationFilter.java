package org.whitestryder.labs.config.security.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.whitestryder.labs.api.support.StatusCodeChangingHttpServletResponseWrapper;

import io.jsonwebtoken.ExpiredJwtException;

public class StatelessAuthenticationFilter extends GenericFilterBean {

	private static final Logger LOG = LoggerFactory.getLogger(StatelessAuthenticationFilter.class);
	
    private final TokenAuthenticationService authenticationService;

    public StatelessAuthenticationFilter(TokenAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        try {
	        Authentication authentication = authenticationService.getAuthentication(httpRequest);
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        filterChain.doFilter(request, response);
	        SecurityContextHolder.getContext().setAuthentication(null);
        } catch (ExpiredJwtException expJwtEx){
        	HttpServletResponse httpResponse = (HttpServletResponse)response;
        	LOG.info(String.format("JWT Token expired, details: %s", expJwtEx.getMessage()));
        	StatusCodeChangingHttpServletResponseWrapper wrapper = 
        			new StatusCodeChangingHttpServletResponseWrapper(httpResponse,
        					HttpStatus.INTERNAL_SERVER_ERROR.value(),
        					HttpStatus.UNAUTHORIZED.value());
        	filterChain.doFilter(request, wrapper.getResponse());
        }
       
    }
}
