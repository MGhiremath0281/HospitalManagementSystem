package com.springboot.hospitalmgmt.HospitalManagement.dto.auth;

public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer"; // Added standard token type

    // Changed field name to accessToken for clarity
    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}