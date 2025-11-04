package com.springboot.hospitalmgmt.HospitalManagement.dto.auth;

/**
 * Data Transfer Object (DTO) for handling authentication requests (login and
 * registration).
 * It includes email, password, and name (required for registration).
 */
public class AuthRequest {
    private String email;
    private String password;
    private String name; // Added name field to support registration

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Required getter for registration, resolving the compilation error
    public String getName() {
        return name;
    }

    // Required setter for registration
    public void setName(String name) {
        this.name = name;
    }
}
