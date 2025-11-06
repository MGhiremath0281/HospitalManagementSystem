package com.springboot.hospitalmgmt.HospitalManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.DoctorRegisterDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.LoginDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.PatientRegisterDTO;
import com.springboot.hospitalmgmt.HospitalManagement.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody PatientRegisterDTO dto) {
        String response = authService.registerPatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO dto) {
        String response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/doctor")
    public String registerDoctor(@RequestBody DoctorRegisterDTO dto) {
        return authService.registerDoctor(dto);
    }
}
