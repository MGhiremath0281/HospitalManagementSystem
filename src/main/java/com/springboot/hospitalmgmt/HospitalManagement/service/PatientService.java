package com.springboot.hospitalmgmt.HospitalManagement.service;

import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import com.springboot.hospitalmgmt.HospitalManagement.repository.PatientRepository;

import org.springframework.data.domain.Page;  
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);

    @Autowired
    private PatientRepository patientRepository;

    public Patient savePatient(Patient patient) {
        logger.info("Saving patient: {}", patient.getName());
        return patientRepository.save(patient);
    }

    public Page<Patient> getAllPatients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        logger.debug("Fetching all patients from database...");
        return patientRepository.findAll(pageable);  
    }

    public Optional<Patient> getPatientById(Long id) {
        logger.debug("Fetching patient by ID: {}", id);
        return patientRepository.findById(id);
    }

    public void deletePatient(Long id) {
        logger.info("Deleting patient with ID: {}", id);
        patientRepository.deleteById(id);
    }
}
