package com.quantitymeasurement.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.quantitymeasurement.dto.AuthResponseDTO;
import com.quantitymeasurement.entity.User;
import com.quantitymeasurement.repository.UserRepository;
import com.quantitymeasurement.security.JwtUtil;

@Service
public class GoogleAuthServiceImpl implements GoogleAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private static final String CLIENT_ID = "YOUR_GOOGLE_CLIENT_ID";

    @Override
    public AuthResponseDTO authenticateWithGoogle(String idToken) {

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    new JacksonFactory()
            )
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            GoogleIdToken idTokenObj = verifier.verify(idToken);

            if (idTokenObj == null) {
                throw new RuntimeException("Invalid Google Token");
            }

            Payload payload = idTokenObj.getPayload();

            String email = payload.getEmail();

            // 🔥 Check DB
            User user = userRepository.findByUsername(email)
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setUsername(email);
                        newUser.setPassword("GOOGLE_USER"); // dummy
                        newUser.setRole("ROLE_USER");
                        return userRepository.save(newUser);
                    });

            //  Generate JWT
            String jwt = jwtUtil.generateToken(user.getUsername());

            return new AuthResponseDTO(jwt);

        } catch (Exception e) {
            throw new RuntimeException("Google authentication failed");
        }
    }
}
