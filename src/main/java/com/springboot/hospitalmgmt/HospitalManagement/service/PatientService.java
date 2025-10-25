package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import com.springboot.hospitalmgmt.HospitalManagement.repository.PatientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    
    public Patient savePatient(Patient patient) {
        logger.info("Saving patient: {}", patient.getName());

        try {
            Patient saved = patientRepository.save(patient);
            logger.info("Patient saved successfully with ID: {}", saved.getId());
            return saved;
        } catch (Exception e) {
            logger.error("Error while saving patient: {}", e.getMessage(), e);
            throw e;
        }
    }

    
    public List<Patient> getAllPatients() {
        logger.debug("Fetching all patients from database...");
        return patientRepository.findAll();
    }

   
    public Optional<Patient> getPatientById(Long id) {
        logger.debug("Fetching patient by ID: {}", id);
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            logger.info("Found patient: {}", patient.get().getName());
        } else {
            logger.warn("No patient found with ID: {}", id);
        }
        return patient;
    }

    public void deletePatient(Long id) {
        logger.info("Deleting patient with ID: {}", id);
        try {
            patientRepository.deleteById(id);
            logger.info("Deleted patient with ID: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting patient ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
