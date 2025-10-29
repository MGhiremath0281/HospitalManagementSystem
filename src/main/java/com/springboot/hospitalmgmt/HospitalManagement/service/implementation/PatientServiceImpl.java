package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import com.springboot.hospitalmgmt.HospitalManagement.exceptions.PatientNotFoundException;
import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import com.springboot.hospitalmgmt.HospitalManagement.repository.PatientRepository;
import com.springboot.hospitalmgmt.HospitalManagement.service.PatientService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient createPatient(Patient patient) {
        logger.info("Creating new patient: {}", patient.getName());
        return patientRepository.save(patient);
    }

    @Override
    public Page<Patient> getAllPatients(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        logger.debug("Fetching patients with pagination - Page: {}, Size: {}", page, size);
        return patientRepository.findAll(pageable);
    }

    @Override
    public Patient getPatientById(Long id) {
        logger.debug("Fetching patient with ID: {}", id);
        return patientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Patient not found with ID: {}", id);
                    return new PatientNotFoundException(id);
                });
    }

    @Override
    public Patient updatePatient(Long id, Patient patient) {
        logger.info("Updating patient with ID: {}", id);
        return patientRepository.findById(id).map(existingPatient -> {
            existingPatient.setName(patient.getName());
            existingPatient.setAge(patient.getAge());
            existingPatient.setGender(patient.getGender());
            logger.debug("Updated details for patient ID: {}", id);
            return patientRepository.save(existingPatient);
        }).orElseThrow(() -> {
            logger.error("Cannot update - Patient not found with ID: {}", id);
            return new PatientNotFoundException(id);
        });
    }

    @Override
    public void deletePatient(Long id) {
        logger.info("Attempting to delete patient with ID: {}", id);
        if (!patientRepository.existsById(id)) {
            logger.error("Cannot delete - Patient not found with ID: {}", id);
            throw new PatientNotFoundException(id);
        }
        patientRepository.deleteById(id);
        logger.info("Deleted patient with ID: {}", id);
    }
}
