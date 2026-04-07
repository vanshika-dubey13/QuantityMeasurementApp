package com.quantitymeasurement.controller;


import com.quantitymeasurement.dto.AuthRequestDTO;
import com.quantitymeasurement.dto.AuthResponseDTO;
import com.quantitymeasurement.dto.GoogleAuthRequestDTO;
import com.quantitymeasurement.entity.User;
import com.quantitymeasurement.repository.UserRepository;
import com.quantitymeasurement.security.JwtUtil;
import com.quantitymeasurement.service.GoogleAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/*
 * =========================================================
 * Auth Controller (JWT Authentication)
 * =========================================================
 *
 * Handles:
 * - User Registration
 * - User Login (JWT Token generation)
 *
 * Base URL:
 * /auth
 *
 * Endpoints:
 * POST /auth/register
 * POST /auth/login
 * 
 *  
 */

@RestController // Marks this class as REST controller (returns JSON)
@RequestMapping("/auth") // Base URL for all endpoints in this class
public class AuthController {

    /*
     * AuthenticationManager:
     * Used by Spring Security to verify username & password
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private GoogleAuthService googleAuthService;

    /*
     * JwtUtil:
     * Used to generate JWT token after successful login
     */
    @Autowired
    private JwtUtil jwtUtil;

    /*
     * UserRepository:
     * Used to interact with DB (save/find users)
     */
    @Autowired
    private UserRepository userRepository;

    /*
     * PasswordEncoder:
     * Used to encrypt password before storing in DB
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * -------------------------
     *       FLOW (REGISTER)
     * ---------------------------
     *  Swagger
     *      |
     *  AuthController.register()       : handles request
     *      |
     *  AuthRequestDTO (data mapped)    : holds input
     *      |
     *  UserRepository.findByUsername() : DB entity, interaction
     *      |
     *  PasswordEncoder.encode()         : encrypt password
     *      |
     *  User Entity created
     *      |
     *  UserRepository.save()
     *      |
     *  MySQL (users table)
     *      |
     *  Response returned
     *  
     */
    // Think of ResponseEntity as a full HTTP response wrapper.
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequestDTO request) {

    	// Step 1: Check if username already exists in DB
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("User already exists!");
        }

        // Step 2: Create new User object
        User user = new User();
        // Set username from request
        user.setUsername(request.getUsername());
        // Encrypt password before saving (VERY IMPORTANT)
        user.setPassword(passwordEncoder.encode(request.getPassword())); // encrypt password
        // Assign default role
        user.setRole("ROLE_USER"); // default role

        // Step 3: Save user into database
        userRepository.save(user);

        
        // Step 4: Return success response
        return ResponseEntity.ok("User registered successfully!");
    }

    /*
     *  ------------------------
     *       FLOW (LOGIN)
     *  ------------------------    
     *   Swagger
     *      |
     *   AuthController.login()
     *      |
     *   AuthenticationManager.authenticate()             : verifies credentials
     *      |
     *   CustomUserDetailsService.loadUserByUsername()    : loads user
     *      |
     *   UserRepository -> DB
     *      |
     *   User found
     *      |
     *   PasswordEncoder matches password
     *      |
     *   JwtUtil.generateToken()                          : creates token
     *      |
     *   AuthResponseDTO(token)                           : sends token
     *      |
     *   Swagger gets token
     *  
     * 
     * 
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {

        /*
         * Step 1: Authenticate user
         *
         * Spring Security checks:
         * - username exists
         * - password matches (using PasswordEncoder)
         */
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        /*
         * Step 2: If authentication successful,
         * generate JWT token
         */
        String token = jwtUtil.generateToken(request.getUsername());
        
        /*
         * Step 3: Return token to client
         */
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
    
    @PostMapping("/google")
    public ResponseEntity<AuthResponseDTO> googleLogin(@RequestBody GoogleAuthRequestDTO request) {

        AuthResponseDTO response = googleAuthService.authenticateWithGoogle(request.getIdToken());

        return ResponseEntity.ok(response);
    }
}