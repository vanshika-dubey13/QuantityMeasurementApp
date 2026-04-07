package com.quantitymeasurement.security;


import com.quantitymeasurement.entity.User;
import com.quantitymeasurement.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * =========================================================
 * Custom User Details Service
 * =========================================================
 *
 * Purpose:
 * This class tells Spring Security HOW to load user data
 * from the database during authentication.
 *
 * It acts as a bridge between:
 * Spring Security <-> Database (User entity)
 * 
 * 
 * UserDetailsService is a prebuilt interface provided by Spring Security.
 * It tells Spring Security HOW to fetch user data during login
 */
@Service  // Marks this as a Spring service component
public class CustomUserDetailsService implements UserDetailsService {

    /*
     * Repository used to fetch user from database
     */
    @Autowired
    private UserRepository repository;

    
    /*
     * =========================================================
     * loadUserByUsername()
     * =========================================================
     *
     * This method is called automatically by Spring Security
     * during login.
     *
     * Input:
     * username (from login request)
     *
     * Output:
     * UserDetails object (Spring Security format)
     * 
     * 
     *  UsernameNotFoundException ?
     *  - It is a prebuilt exception class provided by Spring Security.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        /*
         * Step 1: Fetch user from database
         * 
         * User implements UserDetails interface of spring security
         */
        User user = repository
        		.findByUsername(username)
                .orElseThrow(
                		() -> new UsernameNotFoundException("User not found"));

        /*
         * Step 2: Convert our User entity -> Spring Security User
         *
         * Spring Security requires:
         * - username
         * - password
         * - roles/authorities
         */
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), // username
                user.getPassword(), // encrypted password
                /* It represents a role or permission
                 * 
                 *  Spring expects: Collection<? extends GrantedAuthority>
                 *  So you convert your role string into: new SimpleGrantedAuthority("ROLE_USER")
                 *  
                 *  Why List.of(...)?
                 *  - Because Spring expects a collection of roles
                 *  
                 */
                List.of(new SimpleGrantedAuthority(user.getRole()))  // role
        );
    }
}
