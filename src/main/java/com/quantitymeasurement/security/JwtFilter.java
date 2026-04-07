package com.quantitymeasurement.security;



import jakarta.servlet.*;
import jakarta.servlet.http.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
 * =========================================================
 * JWT Filter
 * =========================================================
 *
 * Purpose:
 * This filter runs on EVERY request.
 *
 * It:
 * - Extracts JWT token from request
 * - Validates the token
 * - Sets authentication in Spring Security context
 *
 * Without this:
 *  JWT token will never be checked
 *  All secured APIs will fail
 *  
 *  
 *  Request comes with JWT
 *       |
 *   JwtFilter intercepts
 *       |
 *   Extract token
 *       |
 *   Extract username
 *       |
 *   Load user from DB
 *       |
 *   Validate token
 *       |
 *   Create Authentication object
 *       |
 *   Set in SecurityContext
 *       |
 *   Forward request
 *       |
 *   Controller executes (user is authenticated)
 */

@Component
public class JwtFilter extends OncePerRequestFilter {

    /*
     * JwtUtil:
     * Used to extract username & validate token
     */
    @Autowired
    private JwtUtil jwtUtil;

    /*
     * CustomUserDetailsService:
     * Used to load user from DB
     */
    @Autowired
    private CustomUserDetailsService userDetailsService;

    
    /*
     * This method runs for EVERY HTTP request
     * 
     * Request -> Filter -> Controller
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, // Contains incoming request data
                                   HttpServletResponse response,// Used to send response back
                                   FilterChain chain)           // This is what moves request forward
            throws ServletException, IOException {


        /*
         * Step 1: Get Authorization header
         *
         * Example:
         * Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
         */
        final String authHeader = request.getHeader("Authorization");

        String username = null;
        String token = null;

        
        /*
         * Step 2: Extract token
         */
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            
            // Remove "Bearer " prefix
        	token = authHeader.substring(7);
        	
            // Extract username from token
            username = jwtUtil.extractUsername(token);
        }

        
        /*
         * Step 3: If username exists AND user is not already authenticated
         */
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            /*
             * Load user details from database
             */
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            
            /*
             * Step 4: Validate token
             */
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {

            	
                /*
                 * Step 5: Create authentication object
                 */
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                /*
                 * Attach request details (IP, session, etc.)
                 */
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                /*
                 * Step 6: Set authentication in Security Context
                 *
                 * This means:
                 * "User is now authenticated"
                 */
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        /*
         * Step 7: Continue request flow
         */
        chain.doFilter(request, response);
    }
}