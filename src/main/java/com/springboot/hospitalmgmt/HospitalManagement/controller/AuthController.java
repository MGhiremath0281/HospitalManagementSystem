package com.springboot.hospitalmgmt.HospitalManagement.controller;

import com.springboot.hospitalmgmt.HospitalManagement.dto.Login.LoginiRequestDto;
import com.springboot.hospitalmgmt.HospitalManagement.dto.Login.LoginresponseDto;
import com.springboot.hospitalmgmt.HospitalManagement.dto.SignupResponseDto;
import com.springboot.hospitalmgmt.HospitalManagement.service.implementation.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authServiceimpl;

    public AuthController(AuthServiceImpl authServiceimpl) {
        this.authServiceimpl = authServiceimpl;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginresponseDto> login(@Valid @RequestBody LoginiRequestDto loginiRequestDto) {
        return ResponseEntity.ok(authServiceimpl.login(loginiRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody LoginiRequestDto loginiRequestDto) {
        return ResponseEntity.ok(authServiceimpl.signup(loginiRequestDto));
    }
}
