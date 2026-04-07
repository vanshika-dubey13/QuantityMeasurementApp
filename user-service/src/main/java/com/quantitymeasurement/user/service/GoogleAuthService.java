package com.quantitymeasurement.user.service;

import com.quantitymeasurement.user.dto.AuthResponseDTO;

public interface GoogleAuthService {
    AuthResponseDTO authenticateWithGoogle(String idToken);
}