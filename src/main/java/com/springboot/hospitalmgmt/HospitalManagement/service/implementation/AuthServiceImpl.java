package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.AuthRequest;
import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.AuthResponse;
import com.springboot.hospitalmgmt.HospitalManagement.models.User;
import com.springboot.hospitalmgmt.HospitalManagement.repository.UserRepository;
import com.springboot.hospitalmgmt.HospitalManagement.security.JwtUtil;
import com.springboot.hospitalmgmt.HospitalManagement.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Authenticates the user and returns the JWT token and user details,
     * using the saved name from the database.
     */
    @Override
    public AuthResponse login(AuthRequest request) {
        // 1. Authenticate the user credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // 2. Retrieve the full User object to get the saved name
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        // 3. Generate JWT
        String token = jwtUtil.generateToken(user);

        // 4. Return AuthResponse using the actual name retrieved from the User entity
        return new AuthResponse(token, user.getEmail(), user.getName());
    }

    /**
     * Registers a new user. It does NOT generate a JWT token.
     * The return type is changed from AuthResponse to void, and the name
     * is correctly extracted from the AuthRequest.
     */
    @Override
    public void register(AuthRequest request) {
        // Simple check to prevent duplicate emails
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered.");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // FIX: Use the name provided in the AuthRequest for registration
        user.setName(request.getName());

        user.setEnabled(true);
        userRepository.save(user);

        // Removed JWT generation and AuthResponse return to enforce login for token
        // retrieval.
    }
}
