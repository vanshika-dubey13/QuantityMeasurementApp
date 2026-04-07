package com.quantitymeasurement.repository;


import com.quantitymeasurement.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * =========================================================
 * User Repository
 * =========================================================
 *
 * Purpose:
 * This interface is responsible for interacting with the database
 * for the User entity.
 *
 * It acts as a bridge between:
 * Service/Controller -> Database
 *
 * Spring Data JPA provides:
 * - save()
 * - findById()
 * - findAll()
 * - delete()
 *
 * Custom Methods:
 * - findByUsername() -> used for login authentication
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /*
     * Custom Method:
     * Find user by username
     *
     * Spring automatically generates query like:
     * SELECT * FROM users WHERE username = ?
     *
     * Used in:
     * - Login authentication
     * - JWT validation
     * 
     * 
     * here Optional means:
     * - This method may return a User OR it may return nothing (null), but safely
     */
    Optional<User> findByUsername(String username);
}