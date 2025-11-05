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
import org.springframework.web.bind.annotation.*;

import com.springboot.hospitalmgmt.HospitalManagement.dto.patient.PatientRequestDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.patient.PatientResponseDTO;
import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import com.springboot.hospitalmgmt.HospitalManagement.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patients")
public class PatientControllers {

    private static final Logger logger = LoggerFactory.getLogger(PatientControllers.class);

    @Autowired
    private PatientService patientService;

    // ---------------------- PUBLIC ENDPOINTS ----------------------
    @PostMapping("/register")
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO dto) {
        // Anyone can register as a patient
        PatientResponseDTO response = patientService.createPatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ---------------------- PRIVATE ENDPOINTS ----------------------

    // Only doctors/staff/admins can view all patients
    @PreAuthorize("hasAnyRole('DOCTOR','STAFF','ADMIN')")
    @GetMapping
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatients(
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<PatientResponseDTO> patients = patientService.getAllPatients(pageable);
        return ResponseEntity.ok(patients);
    }

    // Patients can view their own data; doctors/staff/admins can view any patient
    @PreAuthorize("hasRole('PATIENT') and #id == principal.id or hasAnyRole('DOCTOR','STAFF','ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        logger.info("Fetching patient by id: {}", id);
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    // Only doctors/staff/admins can update patient data
    @PreAuthorize("hasAnyRole('DOCTOR','STAFF','ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDTO dto) {

        PatientResponseDTO updatedPatient = patientService.updatePatient(id, dto);
        return ResponseEntity.ok(updatedPatient);
    }

    // Only doctors/staff/admins can delete patient data
    @PreAuthorize("hasAnyRole('DOCTOR','STAFF','ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
