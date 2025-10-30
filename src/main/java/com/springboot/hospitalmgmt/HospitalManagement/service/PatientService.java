package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.dto.patient.PatientRequestDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.patient.PatientResponseDTO;
import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PatientService {
    Patient createPatient(PatientRequestDTO dto);
    Page<PatientResponseDTO> getAllPatients(Pageable pageable);
    Patient getPatientById(Long id);
    Patient updatePatient(Long id, PatientRequestDTO dto);
    void deletePatient(Long id);
}
