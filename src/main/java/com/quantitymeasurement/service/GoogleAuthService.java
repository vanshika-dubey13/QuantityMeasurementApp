package com.quantitymeasurement.service;

import com.quantitymeasurement.dto.AuthResponseDTO;

public interface GoogleAuthService {
    AuthResponseDTO authenticateWithGoogle(String idToken);
}
