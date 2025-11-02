package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import com.springboot.hospitalmgmt.HospitalManagement.dto.Login.LoginiRequestDto;
import com.springboot.hospitalmgmt.HospitalManagement.dto.Login.LoginresponseDto;
import com.springboot.hospitalmgmt.HospitalManagement.dto.SignupResponseDto;
import com.springboot.hospitalmgmt.HospitalManagement.models.User;
import com.springboot.hospitalmgmt.HospitalManagement.repository.UserRepositry;
import com.springboot.hospitalmgmt.HospitalManagement.security.AuthUtil;
import com.springboot.hospitalmgmt.HospitalManagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepositry userRepositry;
    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepositry userRepositry,
                           AuthenticationManager authenticationManager,
                           AuthUtil authUtil,
                           PasswordEncoder passwordEncoder) {
        this.userRepositry = userRepositry;
        this.authenticationManager = authenticationManager;
        this.authUtil = authUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginresponseDto login(@Valid LoginiRequestDto loginiRequestDto) {
        // Authenticate credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginiRequestDto.getUsername(),
                        loginiRequestDto.getPassword()
                )
        );

        // Get authenticated user
        User user = (User) authentication.getPrincipal();

        // Generate JWT token
        String token = authUtil.generateAccessToken(user);

        // Return response with user ID and token
        return new LoginresponseDto(user.getId(), token);
    }

    @Override
    public SignupResponseDto signup(@Valid LoginiRequestDto signupRequestDto) {
        // Check if user already exists
        User existingUser = userRepositry.findByUsername(signupRequestDto.getUsername()).orElse(null);
        if (existingUser != null) {
            throw new IllegalArgumentException("User already exists");
        }

        // Create a new user using constructor (no builder now)
        User user = new User();
        user.setUsername(signupRequestDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));

        // Save to DB
        userRepositry.save(user);

        // Return success response
        return new SignupResponseDto(
                user.getId(),
                user.getUsername(),
                "Signup successful"
        );
    }
}
