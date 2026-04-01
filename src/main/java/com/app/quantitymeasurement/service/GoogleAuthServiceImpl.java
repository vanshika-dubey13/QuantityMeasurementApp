package com.app.quantitymeasurement.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.app.quantitymeasurement.dto.AuthResponseDTO;
import com.app.quantitymeasurement.entity.User;
import com.app.quantitymeasurement.repository.UserRepository;
import com.app.quantitymeasurement.security.JwtUtil;

@Service
public class GoogleAuthServiceImpl implements GoogleAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String CLIENT_ID = "YOUR_GOOGLE_CLIENT_ID";

    @Override
    public AuthResponseDTO authenticateWithGoogle(String idTokenString) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance()
            )
            .setAudience(Collections.singletonList(CLIENT_ID))
            .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                throw new RuntimeException("Invalid Google Token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            String email = payload.getEmail();

            User user = userRepository.findByUsername(email)
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setUsername(email);
                        newUser.setPassword("GOOGLE_USER"); // dummy password
                        newUser.setRole("ROLE_USER");       // default role
                        return userRepository.save(newUser);
                    });

            String jwt = jwtUtil.generateToken(user.getUsername());

            return new AuthResponseDTO(jwt);

        } catch (Exception e) {
            throw new RuntimeException("Google authentication failed: " + e.getMessage(), e);
        }
    }
}