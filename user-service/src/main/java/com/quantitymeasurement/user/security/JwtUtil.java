package com.quantitymeasurement.user.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "mysecretkeymysecretkeymysecretkey"; 
    private final long EXPIRATION_TIME = 1000 * 60 * 60; 

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username) {
    	return Jwts.builder()
    	        .setSubject(username) 
    	        .setIssuedAt(new Date()) 
    	        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  
    	        .signWith(getSigningKey()) 
    	        .compact();  
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder() 
                .setSigningKey(getSigningKey()) 
                .build()
                .parseClaimsJws(token) 
                .getBody();  
    }
}
