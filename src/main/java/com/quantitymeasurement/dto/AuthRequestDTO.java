package com.quantitymeasurement.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

/*
 * =========================================================
 * AuthRequestDTO
 * =========================================================
 *
 * Purpose:
 * This class represents the data sent by client (Swagger/Postman)
 * when performing:
 * - User Registration
 * - User Login
 *
 * It acts as a bridge between:
 * Client (JSON request) -> Controller (Java object)
 *
 * Example JSON input:
 * {
 *   "username": "sarthak",
 *   "password": "123456"
 * }
 * 
 * Instead of handling raw JSON manually:
 * 
 * Client (Swagger/Postman)
 *       | JSON
 *  AuthRequestDTO (Java object)
 *       |
 *  AuthController
 *       |
 *  Service / Security
 * 
 * 
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthRequestDTO {

    /*
     * Username field
     *
     * @NotBlank:
     * - Ensures value is not null, not empty, not just spaces
     *
     * @Size:
     * - Minimum 3 characters
     * - Maximum 20 characters
     */
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    
    
    /*
     * Password field
     *
     * @NotBlank:
     * - Prevents empty passwords
     *
     * @Size:
     * - Minimum length required for security
     */
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;
}