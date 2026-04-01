package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.AuthResponseDTO;

/**
 * Service interface for Google OAuth authentication.
 *
 * Implemented by GoogleAuthServiceImpl to:
 * - Verify Google ID tokens
 * - Create or fetch users
 * - Return JWT tokens
 */
public interface GoogleAuthService {

    /**
     * Authenticates a user with a Google ID token.
     *
     * @param idToken the Google ID token received from the client
     * @return AuthResponseDTO containing the JWT token for the user
     */
    AuthResponseDTO authenticateWithGoogle(String idToken);
}