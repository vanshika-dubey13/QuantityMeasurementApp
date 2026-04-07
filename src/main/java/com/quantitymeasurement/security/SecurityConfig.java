package com.quantitymeasurement.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/*
 * =========================================================
 * Security Configuration
 * =========================================================
 *
 * This class configures:
 * - CORS (allows React frontend on port 5173 to call this backend)
 * - Authentication (login)
 * - Authorization (who can access what)
 * - JWT filter integration
 *
 * Client (React @ localhost:5173)
 *       |
 *  CORS filter (allows cross-origin requests)
 *       |
 *  Request hits SecurityFilterChain
 *       |
 *  JwtFilter runs FIRST
 *       |
 *  Check token
 *       |
 *  If valid    ->  allow request
 *  If invalid  ->  reject (401/403)
 *       |
 *  Controller executes
 */


@Configuration // Marks this as Spring configuration class
@EnableWebSecurity // Enables Spring Security in the application
public class SecurityConfig {
	
    /*
     * JwtFilter:
     * Custom filter that checks JWT token in every request
     */
    @Autowired
    private JwtFilter jwtFilter;

    /*
     * =========================================================
     * CORS CONFIGURATION
     * =========================================================
     *
     * Allows the React frontend (running on localhost:5173 via Vite)
     * to make HTTP requests to this Spring Boot backend (localhost:8080).
     *
     * Without this, the browser blocks cross-origin requests.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Allow React dev server origins
        config.setAllowedOrigins(Arrays.asList(
                "http://localhost:5173",  // Vite default port
                "http://localhost:3000",  // CRA default port (fallback)
                "http://127.0.0.1:5173"
        ));

        // Allow all standard HTTP methods
        config.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
        ));

        // Allow Authorization header (for JWT) and Content-Type
        config.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept",
                "X-Requested-With"
        ));

        // Allow credentials (cookies, Authorization headers)
        config.setAllowCredentials(true);

        // Cache pre-flight response for 1 hour
        config.setMaxAge(3600L);

        // Expose Authorization header to frontend
        config.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply to ALL endpoints
        return source;
    }

    /*
     * =========================================================
     * PASSWORD ENCODER
     * =========================================================
     *
     * Used to encrypt passwords before saving in DB
     * and to match passwords during login
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // strong hashing algorithm
    }

    /*
     * =========================================================
     * AUTHENTICATION MANAGER
     * =========================================================
     *
     * Used to authenticate user credentials during login
     *
     * Internally:
     * - Calls CustomUserDetailsService
     * - Compares password using PasswordEncoder
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /*
     * =========================================================
     * SECURITY FILTER CHAIN
     * =========================================================
     *
     * This defines:
     * - CORS is enabled
     * - Which APIs are public
     * - Which APIs require authentication
     * - Where JWT filter should run
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        /*
         * Enable CORS using the bean defined above
         */
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        /*
         * Disable CSRF (not needed for stateless REST APIs using JWT)
         */
        .csrf(csrf -> csrf.disable())
        /*
         * Define authorization rules
         */
        .authorizeHttpRequests(auth -> auth
                /*
                 * Public APIs (no authentication needed):
                 * - /auth/** -> login & register endpoints
                 * - /swagger-ui/** -> Swagger UI
                 * - /v3/api-docs/** -> OpenAPI docs
                 */
                .requestMatchers(
                        "/auth/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()
                /*
                 * All other APIs require a valid JWT token
                 */
                .anyRequest().authenticated()
        );

        /*
         * Add JWT filter BEFORE Spring's default authentication filter.
         *
         * This ensures JWT is validated on every request
         * before it reaches the controller.
         */
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        /*
         * Build and return security configuration
         */
        return http.build();
    }
}
