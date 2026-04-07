package com.quantitymeasurement.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import java.util.Date;

/*
 * =========================================================
 * JWT Utility Class
 * =========================================================
 *
 * Purpose:
 * This class handles everything related to JWT:
 * - Generate token (during login)
 * - Extract data from token
 * - Validate token
 */

@Component
public class JwtUtil {

    /*
     * Secret key used to sign the token
     * MUST be at least 32 characters for HS256
     */
    private final String SECRET = "mysecretkeymysecretkeymysecretkey"; // ≥ 32 chars
    
    /*
     * Token expiration time (1 hour)
     */
    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    /*
     * =========================================================
     * Generate Signing Key
     * =========================================================
     *
     * Converts SECRET string into a secure key object
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }


    
    /*
     * =========================================================
     * Generate JWT Token
     * =========================================================
     *
     * Called during login
     *
     * Steps:
     * 1. Set username as subject
     * 2. Set issue time
     * 3. Set expiration
     * 4. Sign token using secret key
     */
    public String generateToken(String username) {

    	

    	return Jwts.builder()
    	        .setSubject(username) // stores username inside token
    	        .setIssuedAt(new Date()) // current time
    	        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // expiry
    	        .signWith(getSigningKey()) // sign token
    	        .compact();  // build token string
    }

    /*
     * =========================================================
     * Extract Username from Token
     * =========================================================
     *
     * Reads token payload and returns username
     */
    public String extractUsername(String token) {

        return getClaims(token).getSubject();
    }

    /*
     * =========================================================
     * Validate Token
     * =========================================================
     *
     * Checks:
     * - Username matches
     * - Token is not expired
     */
    public boolean validateToken(String token, String username) {

        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    /*
     * =========================================================
     * Check Token Expiry
     * =========================================================
     */
    private boolean isTokenExpired(String token) {

        return getClaims(token).getExpiration().before(new Date());
    }

    
    /*
     * =========================================================
     * Extract Claims
     * =========================================================
     *
     * Claims = data stored inside token
     *
     * Steps:
     * 1. Parse token
     * 2. Verify signature
     * 3. Return payload
     */
    private Claims getClaims(String token) {

        return Jwts.parserBuilder() 
                .setSigningKey(getSigningKey()) // verify signature
                .build()
                .parseClaimsJws(token) // parse token
                .getBody();  // extract payload
    }
}