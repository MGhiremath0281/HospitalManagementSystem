package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PatientService {
    Patient createPatient(Patient patient);
    Page<Patient> getAllPatients(int page, int size);
    Optional<Patient> getPatientById(Long id);
    Patient updatePatient(Long id, Patient patient);
    void deletePatient(Long id);
}
