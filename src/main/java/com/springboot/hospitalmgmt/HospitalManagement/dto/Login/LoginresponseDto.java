package com.springboot.hospitalmgmt.HospitalManagement.dto.Login;

public class LoginresponseDto {
    private Long userId;
    private String token;

    public LoginresponseDto(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
