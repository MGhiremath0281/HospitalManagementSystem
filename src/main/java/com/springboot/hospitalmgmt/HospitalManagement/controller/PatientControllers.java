package com.springboot.hospitalmgmt.HospitalManagement.controller;

import com.springboot.hospitalmgmt.HospitalManagement.dto.patient.PatientRequestDTO;
import com.springboot.hospitalmgmt.HospitalManagement.dto.patient.PatientResponseDTO;
import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import com.springboot.hospitalmgmt.HospitalManagement.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/public/api/patients")
public class PatientControllers {
    private static final Logger logger = LoggerFactory.getLogger(PatientControllers.class);

    @Autowired
    private PatientService patientService;

    // Create Patient
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(@Valid @RequestBody PatientRequestDTO dto) {
        PatientResponseDTO response = patientService.createPatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // Get All Patients with Pagination
    @GetMapping
    public ResponseEntity<Page<PatientResponseDTO>> getAllPatients(
            @PageableDefault(page = 0, size = 2, sort = "id") Pageable pageable) {
        Page<PatientResponseDTO> patients = patientService.getAllPatients(pageable);
        return ResponseEntity.ok(patients);
    }

    // Get Patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        logger.info("Fetching the  patient by id : {}",id);
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    // Update Patient
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientRequestDTO dto) {

        PatientResponseDTO updatedPatient = patientService.updatePatient(id, dto);

        return ResponseEntity.ok(updatedPatient);
    }

    // Delete Patient
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
