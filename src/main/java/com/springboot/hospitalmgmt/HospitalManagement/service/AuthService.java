package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.dto.Login.LoginiRequestDto;
import com.springboot.hospitalmgmt.HospitalManagement.dto.Login.LoginresponseDto;
import com.springboot.hospitalmgmt.HospitalManagement.dto.SignupResponseDto;
import jakarta.validation.Valid;

public interface AuthService {
    LoginresponseDto login(LoginiRequestDto loginiRequestDto);
    SignupResponseDto signup(LoginiRequestDto signupRequestDto);

}
