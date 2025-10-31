package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.models.Doctor;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface DocterService {
Doctor createDocter(Doctor doctor);
Page<Doctor> getAllDocters(int age, int size);
Optional<Doctor> getDocterById(Long id);
Doctor updateDocter(Long id, Doctor doctor);
void deleteDocter(Long id);
}
