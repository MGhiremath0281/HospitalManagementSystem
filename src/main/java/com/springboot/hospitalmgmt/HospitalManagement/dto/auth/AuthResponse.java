package com.springboot.hospitalmgmt.HospitalManagement.dto.auth;

// Remove Lombok imports
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

public class AuthResponse {
    private String token;
    private String email;
    private String name;

    // 1. Manual NoArgsConstructor (replaces @NoArgsConstructor)
    public AuthResponse() {
    }

    // 2. Manual AllArgsConstructor (replaces @AllArgsConstructor)
    public AuthResponse(String token, String email, String name) {
        this.token = token;
        this.email = email;
        this.name = name;
    }

    // 3. Manual Getter methods (replaces @Getter)
    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    // 4. Manual Setter methods (replaces @Setter)
    public void setToken(String token) {
        this.token = token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }
}