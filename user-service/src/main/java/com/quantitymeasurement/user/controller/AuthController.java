package com.quantitymeasurement.user.controller;

import com.quantitymeasurement.user.dto.AuthRequestDTO;
import com.quantitymeasurement.user.dto.AuthResponseDTO;
import com.quantitymeasurement.user.dto.GoogleAuthRequestDTO;
import com.quantitymeasurement.user.entity.User;
import com.quantitymeasurement.user.repository.UserRepository;
import com.quantitymeasurement.user.security.JwtUtil;
import com.quantitymeasurement.user.service.GoogleAuthService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private GoogleAuthService googleAuthService;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(@RequestBody AuthRequestDTO request) {

	    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
	        Map<String, String> error = new HashMap<>();
	        error.put("error", "Email is already in use!");
	        return ResponseEntity.badRequest().body(error);
	    }

	    User user = new User();
	    user.setUsername(request.getUsername());
	    user.setEmail(request.getEmail());
	    user.setPhoneNumber(request.getPhoneNumber());
	    user.setPassword(passwordEncoder.encode(request.getPassword()));
	    user.setRole("ROLE_USER");

	    userRepository.save(user);

	    Map<String, String> response = new HashMap<>();
	    response.put("message", "User registered successfully!");

	    return ResponseEntity.ok(response);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
		// Note: If you want users to login with Email,
		// you'd use request.getEmail() here instead.
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		String token = jwtUtil.generateToken(request.getEmail());
		return ResponseEntity.ok(new AuthResponseDTO(token));
	}

	@PostMapping("/google")
	public ResponseEntity<AuthResponseDTO> googleLogin(@RequestBody GoogleAuthRequestDTO request) {
		AuthResponseDTO response = googleAuthService.authenticateWithGoogle(request.getIdToken());
		return ResponseEntity.ok(response);
	}
}