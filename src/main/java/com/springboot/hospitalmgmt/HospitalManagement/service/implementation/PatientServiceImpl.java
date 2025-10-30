package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import com.springboot.hospitalmgmt.HospitalManagement.dto.patient.PatientRequestDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.patient.PatientResponseDTO;
import com.springboot.hospitalmgmt.HospitalManagement.exceptions.PatientNotFoundException;
import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import com.springboot.hospitalmgmt.HospitalManagement.repository.PatientRepository;
import com.springboot.hospitalmgmt.HospitalManagement.service.PatientService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    private final PatientRepository patientRepository;

    // **Recommended: Use Constructor Injection for mandatory dependencies**
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // --- CRUD Operations ---

    @Override
    public Patient createPatient(PatientRequestDTO dto) {
        logger.info("Adding new patient: {}", dto.getName());

        // Convert DTO to Entity
        Patient patient = new Patient();
        patient.setName(dto.getName());
        patient.setGender(dto.getGender());
        patient.setAge(dto.getAge());
        patient.setEmail(dto.getEmail());
        patient.setAdmissionDate(dto.getAdmissionDate()); // Assuming AdmissionDate is part of the DTO

        Patient savedPatient = patientRepository.save(patient);
        logger.info("Patient saved with ID: {}", savedPatient.getId());
        return savedPatient; // Returns Entity
    }

    @Override
    public Page<PatientResponseDTO> getAllPatients(Pageable pageable) {
        logger.debug("Fetching patients with pagination");

        Page<Patient> patients = patientRepository.findAll(pageable);

        // Map Patient Entity Page to PatientResponseDTO Page
        Page<PatientResponseDTO> patientDtos = patients.map(patient -> {
            PatientResponseDTO dto = new PatientResponseDTO();
            dto.setId(patient.getId());
            dto.setName(patient.getName());
            dto.setAge(patient.getAge());
            dto.setGender(patient.getGender());
            // Add other necessary fields...
            return dto;
        });
        return patientDtos; // Returns DTO Page
    }

    @Override
    public Patient getPatientById(Long id) {
        logger.debug("Fetching patient with ID: {}", id);
        return patientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Patient not found with ID: {}", id);
                    // Use a concise exception message or a specific constructor for consistency
                    return new PatientNotFoundException("Patient not found with id " + id);
                    // Or simply: return new PatientNotFoundException(id); if the exception class supports it
                }); // Returns Entity
    }

    @Override
    public Patient updatePatient(Long id, PatientRequestDTO dto) {
        logger.info("Updating patient with ID: {}", id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Update failed. Patient not found with ID: {}", id);
                    return new PatientNotFoundException("Patient not found with id " + id);
                });

        // Update fields from DTO
        patient.setName(dto.getName());
        patient.setGender(dto.getGender());
        patient.setAge(dto.getAge());
        patient.setEmail(dto.getEmail());
        patient.setAdmissionDate(dto.getAdmissionDate());

        Patient updatedPatient = patientRepository.save(patient);
        logger.info("Patient updated successfully with ID: {}", updatedPatient.getId());
        return updatedPatient; // Returns Entity
    }

    @Override
    public void deletePatient(Long id) {
        logger.info("Attempting to delete patient with ID: {}", id);
        if (!patientRepository.existsById(id)) {
            logger.error("Cannot delete - Patient not found with ID: {}", id);
            throw new PatientNotFoundException("Patient not found with id " + id);
        }
        patientRepository.deleteById(id);
        logger.info("Deleted patient with ID: {}", id);
    }
}