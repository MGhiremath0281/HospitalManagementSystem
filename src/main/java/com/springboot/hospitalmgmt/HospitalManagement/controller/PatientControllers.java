package com.springboot.hospitalmgmt.HospitalManagement.controller;

import com.springboot.hospitalmgmt.HospitalManagement.models.Patient;
import com.springboot.hospitalmgmt.HospitalManagement.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/patients")
public class PatientControllers {
    private static final Logger logger = LoggerFactory.getLogger(PatientControllers.class);

    @Autowired
    private PatientService patientService;

    // Create Patient
    @PostMapping
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.createPatient(patient));
    }

    // Get All Patients with Pagination
    @GetMapping
    public ResponseEntity<Page<Patient>> getAllPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(patientService.getAllPatients(page, size));
    }

    // Get Patient by ID
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        logger.info("Fetching the  patient by id : {}",id);
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    // Update Patient
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id,@Valid @RequestBody Patient patient) {
        Patient updatedPatient = patientService.updatePatient(id, patient);
        if (updatedPatient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPatient);
    }

    // Delete Patient
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
