package com.springboot.hospitalmgmt.HospitalManagement.dto.auth;

// Remove Lombok imports
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

public class AuthRequest {
    private String email;
    private String password;
    private String name;
    private String role;

    // 1. Manual NoArgsConstructor (replaces @NoArgsConstructor)
    public AuthRequest() {
    }

    // 2. Manual AllArgsConstructor (replaces @AllArgsConstructor)
    public AuthRequest(String email, String password, String name, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    // 3. Manual Getter methods (replaces @Getter)
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    // 4. Manual Setter methods (replaces @Setter)
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }
}