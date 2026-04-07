package com.quantitymeasurement.user.service;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.quantitymeasurement.user.dto.AuthResponseDTO;
import com.quantitymeasurement.user.entity.User;
import com.quantitymeasurement.user.repository.UserRepository;
import com.quantitymeasurement.user.security.JwtUtil;

@Service
public class GoogleAuthServiceImpl implements GoogleAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    //  Fetching the REAL Client ID from application.yml
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

 
    
    @Override
    public AuthResponseDTO authenticateWithGoogle(String idToken) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance()
            )
            .setAudience(Collections.singletonList(clientId))
            .build();

            GoogleIdToken idTokenObj = verifier.verify(idToken);

            if (idTokenObj == null) {
                throw new RuntimeException("Invalid Google Token");
            }

            Payload payload = idTokenObj.getPayload();
            String email = payload.getEmail();

            User user = userRepository.findByUsername(email)
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setUsername(email);
                        newUser.setEmail(email); // FIXED: Added missing email field to prevent SQL null error
                        newUser.setPassword("GOOGLE_USER"); 
                        newUser.setRole("ROLE_USER");
                        return userRepository.save(newUser);
                    });

            String jwt = jwtUtil.generateToken(user.getUsername());
            return new AuthResponseDTO(jwt);

        } catch (Exception e) {
            throw new RuntimeException("Google authentication failed");
        }
    }
}