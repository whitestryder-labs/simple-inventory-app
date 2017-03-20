package org.whitestryder.labs.config.security.auth;

import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public final class TokenHandler {

    private final String secret;
    private UserDetailsService userDetailsService;
    private int tokenTTLSeconds = 0;	//default to 0  which means the token will never expire

    
    public TokenHandler(String secret, UserDetailsService userDetailsService) {
        this.secret = secret;
        this.userDetailsService = userDetailsService;
    }
    
    public TokenHandler(String secret, UserDetailsService userDetailsService, int tokenTTLSeconds) {
        this.secret = secret;
        this.userDetailsService = userDetailsService;
        this.tokenTTLSeconds = tokenTTLSeconds;
    }

    public UserDetails parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userDetailsService.loadUserByUsername(username);
    }

    public String createTokenForUser(UserDetails userDetails) {
    	JwtBuilder builder = Jwts.builder();
    	builder.setSubject(userDetails.getUsername());
    	
    	if (tokenTTLSeconds > 0){
    		ZonedDateTime expirationDateTime = ZonedDateTime.now().plusSeconds(this.tokenTTLSeconds);
    		builder.setExpiration(
    				Date.from(expirationDateTime.toInstant()));
    	}
    	
    	builder.signWith(SignatureAlgorithm.HS512, secret);
    	
        return builder.compact();
    }

}


