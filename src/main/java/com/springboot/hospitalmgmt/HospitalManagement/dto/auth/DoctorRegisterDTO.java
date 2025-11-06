package com.springboot.hospitalmgmt.HospitalManagement.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DoctorRegisterDTO {

    @NotBlank(message = "Name can't be empty")
    @Size(min = 2, max = 30)
    private String name;

    @NotBlank(message = "Specility can't be empty")
    private String spacility;

    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotBlank(message = "Password can't be empty")
    private String password;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpacility() {
        return spacility;
    }

    public void setSpacility(String spacility) {
        this.spacility = spacility;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
