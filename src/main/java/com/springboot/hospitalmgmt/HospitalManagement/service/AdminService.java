package com.springboot.hospitalmgmt.HospitalManagement.service;

import java.util.List;
import java.util.Map;

import com.springboot.hospitalmgmt.HospitalManagement.models.Doctor;
import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;

public interface AdminService {
    Map<String, Object> getDashboardStats();

    List<Patient> getAllPatients();

    List<Doctor> getAllDoctors();

    void deleteUser(Long userId);
}
