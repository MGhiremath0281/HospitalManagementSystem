package com.springboot.hospitalmgmt.HospitalManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.AuthRequest;
import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.AuthResponse;
import com.springboot.hospitalmgmt.HospitalManagement.models.RoleType;
import com.springboot.hospitalmgmt.HospitalManagement.service.AuthService;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Public login endpoint: authenticates user and returns JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    /**
     * Public registration endpoint: creates user with role.
     * Default role is PATIENT. Optional: "role" field in request for doctor/staff.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        try {
            // Determine role based on request input
            RoleType role = RoleType.ROLE_PATIENT; // default
            if (request.getRole() != null) {
                switch (request.getRole().toUpperCase()) {
                    case "DOCTOR" -> role = RoleType.ROLE_DOCTOR;
                    case "STAFF" -> role = RoleType.ROLE_STAFF;
                    case "ADMIN" -> role = RoleType.ROLE_ADMIN;
                }
            }

            // Call service to register user with role
            authService.register(request, Collections.singleton(role));

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully. Please log in to receive your authentication token.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
