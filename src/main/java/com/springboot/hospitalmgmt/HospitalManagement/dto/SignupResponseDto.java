package com.springboot.hospitalmgmt.HospitalManagement.dto;

public class SignupResponseDto {
    private Long id;
    private String username;
    private String message;

    public SignupResponseDto(Long id, String username, String message) {
        this.id = id;
        this.username = username;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
