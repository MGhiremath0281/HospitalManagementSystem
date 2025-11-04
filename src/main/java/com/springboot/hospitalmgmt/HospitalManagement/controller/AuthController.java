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
import com.springboot.hospitalmgmt.HospitalManagement.service.AuthService;

/**
 * Controller class for handling authentication-related requests (login and
 * registration).
 * Ensures that the JWT is only generated and returned upon a successful login
 * request.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Handles user login and generates a JWT token upon successful authentication.
     * The AuthResponse returned is expected to contain the generated JWT and the
     * user's name.
     *
     * @param request The authentication request containing email and password.
     * @return ResponseEntity with AuthResponse (including JWT) on success, or an
     *         error message.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // JWT generation and return is expected here, using the actual name from the DB
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            // Handles incorrect credentials (Invalid email or password)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        } catch (Exception e) {
            // General bad request handling (e.g., malformed data)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    /**
     * Handles user registration. This endpoint only creates the user account
     * and does NOT generate a JWT token, requiring the user to call /login
     * afterwards.
     *
     * @param request The authentication request containing registration details
     *                (email, password, and name).
     * @return ResponseEntity with a success message string on creation, or an error
     *         message.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        try {
            // The service method is now void and only creates the user.
            authService.register(request);
            // Return a simple string confirmation instead of AuthResponse (to ensure no
            // token is returned)
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully. Please log in to receive your authentication token.");
        } catch (Exception e) {
            // Handles validation errors (e.g., email already exists)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
