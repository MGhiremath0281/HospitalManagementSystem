package com.springboot.hospitalmgmt.HospitalManagement.controller;

import com.springboot.hospitalmgmt.HospitalManagement.dto.Login.LoginiRequestDto;
import com.springboot.hospitalmgmt.HospitalManagement.dto.Login.LoginresponseDto;
import com.springboot.hospitalmgmt.HospitalManagement.dto.SignupResponseDto;
import com.springboot.hospitalmgmt.HospitalManagement.service.implementation.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // ✅ changed to match SecurityConfig and OAuth2 URLs
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthServiceImpl authServiceimpl;

    public AuthController(AuthServiceImpl authServiceimpl) {
        this.authServiceimpl = authServiceimpl;
    }

    /**
     * JWT Login Endpoint
     */
    @PostMapping("/login")
    public ResponseEntity<LoginresponseDto> login(@Valid @RequestBody LoginiRequestDto loginiRequestDto) {
        return ResponseEntity.ok(authServiceimpl.login(loginiRequestDto));
    }

    /**
     * User Signup Endpoint
     */
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody LoginiRequestDto loginiRequestDto) {
        return ResponseEntity.ok(authServiceimpl.signup(loginiRequestDto));
    }

    /**
     * Public greeting route
     */
    @GetMapping("/")
    public String greet() {
        return "Welcome to the OAuth2 + JWT Authentication System.";
    }

    /**
     * Google OAuth2 Login Success Handler
     */
    @GetMapping("/oauth2/success")
    public ResponseEntity<?> oauth2Success(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("User not authenticated via Google.");
        }

        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");
        String picture = principal.getAttribute("picture");

        return ResponseEntity.ok(
                "✅ Google Login Successful\n" +
                        "Name: " + name + "\n" +
                        "Email: " + email + "\n" +
                        "Profile Picture: " + picture
        );
    }

    /**
     * Google OAuth2 Login Failure Handler
     */
    @GetMapping("/oauth2/failure")
    public ResponseEntity<?> oauth2Failure() {
        return ResponseEntity.status(401).body("❌ Google Login Failed. Please try again.");
    }
}
