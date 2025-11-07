package com.springboot.hospitalmgmt.HospitalManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.AuthResponseDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.DoctorRegisterDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.LoginDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.PatientRegisterDTO;
import com.springboot.hospitalmgmt.HospitalManagement.security.JwtUtils;
import com.springboot.hospitalmgmt.HospitalManagement.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Handles patient registration.
     */
    @PostMapping("/register/patient")
    public ResponseEntity<String> registerPatient(@Valid @RequestBody PatientRegisterDTO dto) {
        String response = authService.registerPatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Handles doctor registration.
     */
    @PostMapping("/register/doctor")
    public ResponseEntity<String> registerDoctor(@Valid @RequestBody DoctorRegisterDTO dto) {
        String response = authService.registerDoctor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Handles user login and returns a JWT token upon successful authentication.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        // Authenticate user using AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        // Set authenticated user in SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        String token = jwtUtils.generateToken(authentication.getName(), role);

        // Return JWT token in response
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}
