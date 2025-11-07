package com.springboot.hospitalmgmt.HospitalManagement.service.implementation;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.hospitalmgmt.HospitalManagement.dto.patient.PatientRequestDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.patient.PatientResponseDTO;
import com.springboot.hospitalmgmt.HospitalManagement.exceptions.PatientNotFoundException;
import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import com.springboot.hospitalmgmt.HospitalManagement.repository.PatientRepository;
import com.springboot.hospitalmgmt.HospitalManagement.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger logger = LoggerFactory.getLogger(PatientServiceImpl.class);

    @Autowired
    private ModelMapper modelMapper;

    private final PatientRepository patientRepository;

    // **Recommended: Use Constructor Injection for mandatory dependencies**
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // --- CRUD Operations ---

    @Override
    public PatientResponseDTO createPatient(PatientRequestDTO dto) {
        logger.info("Adding new patient: {}", dto.getName());

        // 1. Convert DTO → Entity
        Patient patient = modelMapper.map(dto, Patient.class);

        // 2. Save Entity → Database
        Patient savedPatient = patientRepository.save(patient);
        logger.info("Patient saved with ID: {}", savedPatient.getId());

        // 3. Convert Entity → Response DTO
        PatientResponseDTO responseDTO = modelMapper.map(savedPatient, PatientResponseDTO.class);

        return responseDTO; // Return the DTO
    }

    @Override
    public Page<PatientResponseDTO> getAllPatients(Pageable pageable) {
        logger.debug("Fetching patients with pagination");

        Page<Patient> patients = patientRepository.findAll(pageable);

        // Map Patient Entity Page to PatientResponseDTO Page
        Page<PatientResponseDTO> patientDtos = patients
                .map(patient -> modelMapper.map(patient, PatientResponseDTO.class));

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
                    // Or simply: return new PatientNotFoundException(id); if the exception class
                    // supports it
                }); // Returns Entity
    }

    @Override
    public PatientResponseDTO updatePatient(Long id, PatientRequestDTO dto) {
        logger.info("Updating patient with ID: {}", id);

        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Update failed. Patient not found with ID: {}", id);
                    return new PatientNotFoundException("Patient not found with id " + id);
                });

        // 1. Use ModelMapper to update the EXISTING 'patient' entity from the 'dto'
        // This replaces all the manual 'patient.setName(dto.getName());' lines.
        modelMapper.map(dto, patient);

        Patient updatedPatient = patientRepository.save(patient);

        logger.info("Patient updated successfully with ID: {}", updatedPatient.getId());

        // 2. Use ModelMapper to convert the updated Entity to the Response DTO
        // This replaces all the manual 'responseDTO.setId(updatedPatient.getId());'
        // lines.
        PatientResponseDTO responseDTO = modelMapper.map(updatedPatient, PatientResponseDTO.class);

        return responseDTO;
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