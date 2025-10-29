package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import org.springframework.data.domain.Page;

public interface PatientService {
    Patient createPatient(Patient patient);
    Page<Patient> getAllPatients(int page, int size);
    Patient getPatientById(Long id);
    Patient updatePatient(Long id, Patient patient);
    void deletePatient(Long id);
}
