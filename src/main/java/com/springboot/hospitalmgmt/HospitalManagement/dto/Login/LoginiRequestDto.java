package com.springboot.hospitalmgmt.HospitalManagement.dto.Login;

import lombok.Data;

@Data
public class LoginiRequestDto {
    private String username;
    private String password;

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

    public LoginiRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
