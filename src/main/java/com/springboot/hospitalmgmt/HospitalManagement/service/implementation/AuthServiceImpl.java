package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.AuthRequest;
import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.AuthResponse;
import com.springboot.hospitalmgmt.HospitalManagement.models.RoleType;
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

    @Override
    public AuthResponse login(AuthRequest request) {
        // Authenticate user credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // Retrieve full User object to get saved name
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found after authentication"));

        // Generate JWT token
        String token = jwtUtil.generateToken(user);

        return new AuthResponse(token, user.getEmail(), user.getName());
    }

    @Override
    public void register(AuthRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Assign role based on request
        RoleType role = RoleType.PATIENT; // default
        if ("doctor".equalsIgnoreCase(request.getRole())) {
            role = RoleType.DOCTOR;
        } else if ("staff".equalsIgnoreCase(request.getRole())) {
            role = RoleType.STAFF;
        } else if ("admin".equalsIgnoreCase(request.getRole())) {
            role = RoleType.ADMIN;
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(role))
                .enabled(true)
                .accountLocked(false)
                .build();

        userRepository.save(user);
    }
}
