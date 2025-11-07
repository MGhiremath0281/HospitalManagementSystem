package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.DoctorRegisterDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.LoginDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.auth.PatientRegisterDTO;

public interface AuthService {
    String registerPatient(PatientRegisterDTO dto);

    String registerDoctor(DoctorRegisterDTO dto);

    String login(LoginDTO dto);
}
