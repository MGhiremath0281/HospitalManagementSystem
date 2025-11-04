package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.AuthRequest;
import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.AuthResponse;

/**
 * Service interface for handling authentication operations.
 */
public interface AuthService {

    /**
     * Authenticates a user and generates a JWT token upon success.
     * 
     * @param request The login request.
     * @return AuthResponse containing the JWT token and user details.
     */
    AuthResponse login(AuthRequest request);

    /**
     * Registers a new user. Does not generate a JWT token.
     * 
     * @param request The registration request, which is assumed to contain the
     *                user's name.
     */
    void register(AuthRequest request);
}
