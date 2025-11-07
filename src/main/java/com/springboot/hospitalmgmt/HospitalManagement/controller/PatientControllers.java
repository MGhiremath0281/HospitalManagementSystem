package com.springboot.hospitalmgmt.HospitalManagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.hospitalmgmt.HospitalManagement.dto.patient.PatientRequestDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.patient.PatientResponseDTO;
import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import com.springboot.hospitalmgmt.HospitalManagement.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patient/api")
public class PatientControllers {
    private static final Logger logger = LoggerFactory.getLogger(PatientControllers.class);

    @Autowired
    private PatientService patientService;

    // Only ADMIN can create patients
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO dto) {
        PatientResponseDTO response = patientService.createPatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ADMIN and DOCTOR can view all patients
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR')")
    @GetMapping
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatients(
            @PageableDefault(page = 0, size = 2, sort = "id") Pageable pageable) {
        Page<PatientResponseDTO> patients = patientService.getAllPatients(pageable);
        return ResponseEntity.ok(patients);
    }

    // ADMIN, DOCTOR, and PATIENT can view patient by ID
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'PATIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        logger.info("Fetching patient by id: {}", id);
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    // ADMIN and PATIENT can update patient info
    @PreAuthorize("hasAnyRole('ADMIN', 'PATIENT')")
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDTO dto) {

        PatientResponseDTO updatedPatient = patientService.updatePatient(id, dto);
        return ResponseEntity.ok(updatedPatient);
    }

    // Only ADMIN can delete a patient
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
